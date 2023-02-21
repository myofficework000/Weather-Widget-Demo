package com.example.weatherwidget.database

import android.content.ContentValues
import android.database.Cursor
import com.example.weatherwidget.model.remote.Constant.CITY_COL
import com.example.weatherwidget.model.remote.Constant.CITY_TABLE_NAME
import com.example.weatherwidget.model.remote.Constant.COUNTRY_COL
import com.example.weatherwidget.model.remote.Constant.LAT_COL
import com.example.weatherwidget.model.remote.Constant.LON_COL
import com.example.weatherwidget.model.remote.Constant.ZIP_COL
import com.example.weatherwidget.model.remote.data_zipcode.ZipcodeResponse

class WeatherCityDao(private val databaseHelper: DatabaseHelper) {
    private val db: DatabaseHelper = databaseHelper

    fun addCity(book: ZipcodeResponse) {
        val contentValue = ContentValues().apply {
            book.apply {
                put(CITY_COL, name)
                put(ZIP_COL, zip)
                put(LAT_COL, lat)
                put(LON_COL, lon)
                put(COUNTRY_COL, country)
            }
        }

        db.writableDatabase.apply {
            insert(CITY_TABLE_NAME, null, contentValue)
        }
    }

    private fun getAllCities(): ArrayList<ZipcodeResponse> {
        val cities = ArrayList<ZipcodeResponse>()

        val cursor: Cursor =
            db.readableDatabase.query(CITY_TABLE_NAME, null, null, null, null, null, null, null)

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val cityId = cursor.getInt(0) ?: 0
                val zip = cursor.getString(1) ?: ""
                val city = cursor.getString(2) ?: ""
                val lat = cursor.getString(3) ?: ""
                val lon = cursor.getString(4) ?: ""
                val country = cursor.getString(5) ?: ""
                val cityInfo = ZipcodeResponse(zip, city, lat, lon, country)
                cities.add(cityInfo)
            }
        }
        return cities
    }

    private fun updateCity(zipcodeResponse: ZipcodeResponse) {
        val contentValue = ContentValues().apply {
            zipcodeResponse.apply {
                put(ZIP_COL, zip)
                put(CITY_COL, name)
                put(LAT_COL, lat)
                put(LON_COL, lon)
                put(COUNTRY_COL, country)
            }
        }

        db.writableDatabase.apply {
            update(
                CITY_TABLE_NAME, contentValue,
                "$ZIP_COL = ${zipcodeResponse.zip}", null
            )
        }
    }

    fun deleteCity(zip: String) {
        db.writableDatabase.apply {
            delete(CITY_TABLE_NAME, "$ZIP_COL = $zip", null)
            //close
        }
    }
}