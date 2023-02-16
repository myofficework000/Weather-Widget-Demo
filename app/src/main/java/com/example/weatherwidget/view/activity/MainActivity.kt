package com.example.weatherwidget.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.weatherwidget.R
import com.example.weatherwidget.database.DatabaseHelper
import com.example.weatherwidget.database.WeatherCityDao
import com.example.weatherwidget.databinding.ActivityMainBinding
import com.example.weatherwidget.databinding.AddCityWidgetBinding
import com.example.weatherwidget.fragment.DashboardFragment
import com.example.weatherwidget.model.remote.Constant
import com.example.weatherwidget.model.remote.VolleyHandler
import com.example.weatherwidget.model.remote.data_zipcode.ZipcodeResponse
import com.example.weatherwidget.presenter.mvp_zipcode.MVPZipCode
import com.example.weatherwidget.presenter.mvp_zipcode.ZipcodePresenter
import com.example.weatherwidget.view.fragment.AirPollutionFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity(), MVPZipCode.ZipcodeView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomCityWidgetDlg: BottomSheetDialog
    private lateinit var bindingCityWidget: AddCityWidgetBinding
    private lateinit var zipcodePresenter: ZipcodePresenter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var weatherCityDao: WeatherCityDao
    private lateinit var sharedPreferences: SharedPreferences

    private val dashboardFragment by lazy {
        binding.dashboardFragment.getFragment<DashboardFragment>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSharedPreference()
        initTopAddCityButton()
        initBottomCityWidgetDlg()
        initZipcodePresenter()
        initDatabase()
    }

    private fun initTopAddCityButton() {
        binding.btnShowBottom.shrink()
        binding.btnShowBottom.setOnClickListener {
            bottomCityWidgetDlg.show()
        }
    }

    private fun initSharedPreference() {
        sharedPreferences = this.getSharedPreferences(Constant.SHARED_PREF_FILE, Context.MODE_PRIVATE)
    }

    private fun initDatabase() {
        dbHelper = DatabaseHelper(this.applicationContext)
        weatherCityDao = WeatherCityDao(dbHelper)
    }

    private fun initZipcodePresenter() {
        zipcodePresenter = ZipcodePresenter(VolleyHandler(this), this)
    }

    private fun initBottomCityWidgetDlg() {
        bottomCityWidgetDlg = BottomSheetDialog(this)
        bindingCityWidget = AddCityWidgetBinding.inflate(layoutInflater)
        bottomCityWidgetDlg.setContentView(bindingCityWidget.root)
        bindingCityWidget.btnGetCity.setOnClickListener {
            zipcodePresenter.getZipcodeData(bindingCityWidget.inputZipCode.text.toString(), bindingCityWidget.spinnerCountry.selectedItem.toString())
        }
        bindingCityWidget.btnAddCity.setOnClickListener {
            bindingCityWidget.apply {
                val zipcodeResponse = ZipcodeResponse(inputZipCode.text.toString(), inputCity.text.toString(), "", "", spinnerCountry.selectedItem.toString())
                addCityInfo(zipcodeResponse)
            }
        }
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constant.COUNTRY_CODE)
        bindingCityWidget.spinnerCountry.adapter = spinnerAdapter
        val sharedCityValue = sharedPreferences.getString(Constant.SHARED_PREF_CITY_KEY,"")
        bindingCityWidget.inputCity.setText(sharedCityValue)
    }

    override fun onLoad(isLoading: Boolean) {
        bindingCityWidget.inputCity.setText("Loading...")
    }

    override fun showError(message: String) {
        Toast.makeText(this, "Error getting city info by zipcode", Toast.LENGTH_SHORT).show()
        bindingCityWidget.inputCity.setText("")
    }

    override fun setResult(zipcodeResponse: ZipcodeResponse) {
        bindingCityWidget.inputCity.setText(zipcodeResponse.name)
        addCityInfo(zipcodeResponse)
    }

    fun addCityInfo(zipcodeResponse: ZipcodeResponse) {
        weatherCityDao.addCity(zipcodeResponse)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(Constant.SHARED_PREF_CITY_KEY,zipcodeResponse.name)
        editor.putString(Constant.SHARED_PREF_CITY_LAT,zipcodeResponse.lat)
        editor.putString(Constant.SHARED_PREF_CITY_LON,zipcodeResponse.lon)
        editor.apply()
        editor.commit()
        Toast.makeText(this@MainActivity, "City added!", Toast.LENGTH_SHORT).show()

        dashboardFragment.airPollutionFragment.updateLocation(
            zipcodeResponse.lat.toDouble(),
            zipcodeResponse.lon.toDouble())
    }

}