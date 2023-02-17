package com.example.weatherwidget.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import com.example.weatherwidget.model.remote.data_zipcode.CityValidateResponse
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

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun initBottomCityWidgetDlg() {
        bottomCityWidgetDlg = BottomSheetDialog(this)
        bindingCityWidget = AddCityWidgetBinding.inflate(layoutInflater)
        bottomCityWidgetDlg.setContentView(bindingCityWidget.root)
        bindingCityWidget.btnGetCity.setOnClickListener {
            closeKeyboard(bindingCityWidget.inputZipCode)
            zipcodePresenter.getZipcodeData(bindingCityWidget.inputZipCode.text.toString(), bindingCityWidget.spinnerCountry.selectedItem.toString())

        }
        bindingCityWidget.btnAddCity.setOnClickListener {
            closeKeyboard(bindingCityWidget.inputCity)
            bindingCityWidget.apply {
                zipcodePresenter.getCityValidateData(bindingCityWidget.inputCity.text.toString())
            }
        }
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Constant.COUNTRY_CODE)
        bindingCityWidget.spinnerCountry.adapter = spinnerAdapter
        val sharedCityValue = sharedPreferences.getString(Constant.SHARED_PREF_CITY_KEY,"")
        bindingCityWidget.inputCity.setText(sharedCityValue)
    }

    override fun onLoad(isLoading: Boolean) {
        bindingCityWidget.inputCity.setText(getString(R.string.loading))
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        bindingCityWidget.inputCity.setText("")
    }

    override fun setResult(zipcodeResponse: ZipcodeResponse) {
        bindingCityWidget.inputCity.setText(zipcodeResponse.name)
        addCityInfo(zipcodeResponse)
    }

    override fun setCityValidateResult(cityValidateResponse: CityValidateResponse) {
        bindingCityWidget.inputCity.setText(cityValidateResponse.name)
        if(cityValidateResponse.cod == "200") {
            val zipcodeResponse = ZipcodeResponse(
                "",
                cityValidateResponse.name,
                cityValidateResponse.coord.lat,
                cityValidateResponse.coord.lon,
                cityValidateResponse.sys.country
            )
            addCityInfo(zipcodeResponse)
        } else {
            Toast.makeText(this@MainActivity, cityValidateResponse.message.toString(), Toast.LENGTH_SHORT).show()
        }
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