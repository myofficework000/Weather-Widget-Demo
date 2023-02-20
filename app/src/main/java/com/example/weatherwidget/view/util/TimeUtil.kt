package com.example.weatherwidget.view.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    private const val MINS_PER_DAY = 60 * 24
    private val MS_PER_DAY: Long = (1000 * 60 * TimeUtil.MINS_PER_DAY).toLong()
    private const val SEC = 1000
    private val MIN: Int = TimeUtil.SEC * 60
    private val HOUR: Int = TimeUtil.MIN * 60
    private val DAY: Int = TimeUtil.HOUR * 24
    private val WEEK: Long = (TimeUtil.DAY * 7).toLong()
    private val YEAR: Long = TimeUtil.WEEK * 52
    private val buckets = longArrayOf(
        TimeUtil.YEAR,
        TimeUtil.WEEK,
        TimeUtil.DAY.toLong(),
        TimeUtil.HOUR.toLong(),
        TimeUtil.MIN.toLong(),
        TimeUtil.SEC.toLong()
    )
    private val bucketNames = arrayOf(
        "year", "week", "day",
        "hour", "minute", "second"
    )
    private val statFmtCal: GregorianCalendar = GregorianCalendar()
    private const val format = "yyyy-MM-dd hh:mm:ss"

    // convert milliseconds into the day of the week string
    fun dayStringFormat(dateInput: String): String {

        val df = SimpleDateFormat(format)
        val date: Date = df.parse(dateInput)

        val cal = Calendar.getInstance()
        cal.time = date
        val dow: Int = cal.get(Calendar.DAY_OF_WEEK)
        when (dow) {
            Calendar.MONDAY -> return "Mon"
            Calendar.TUESDAY -> return "Tue"
            Calendar.WEDNESDAY -> return "Wed"
            Calendar.THURSDAY -> return "Thu"
            Calendar.FRIDAY -> return "Fri"
            Calendar.SATURDAY -> return "Sat"
            Calendar.SUNDAY -> return "Sun"
        }
        return "Unknown"
    }
}