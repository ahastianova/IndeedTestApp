package com.atruskova.itunesapitesttask.data

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DataConverter {
        fun dateToString (date: Date?): String {
            var result =
                try {
                    getSimpleDateFormat.format(date)
                } catch (e: Exception) {
                    ""
                }
            return result

        }

    @JvmStatic
    fun getStringDateTimeForView (date: Date?): String{
            var result =
                try {
                    simpleDateTimeFormatForView.format(date)
                } catch (e: Exception) {
                    ""
                }
            return result
        }

        fun stringToDate (dateString: String?): Date? {
            var date =
                try {
                    getSimpleDateFormat.parse(dateString)
                } catch (e: Exception) {
                    null
                }
            return date
        }

    @JvmStatic
    fun priceString(price: String?, currency: String?) : String? {
        if (!price.isNullOrEmpty()) {
            return String.format("%s %s", price, currency)
        }
        return null
    }

    @JvmStatic
    fun timeFormatForView(duration: Long?) : String {
      var result = try {

          String.format("%02d:%02d",
              TimeUnit.MILLISECONDS.toMinutes(duration!!.toLong()),
              TimeUnit.MILLISECONDS.toSeconds(duration.toLong())- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong())))


      } catch (e: Exception) {
          ""
      }
        return result

    }

    var  getSimpleDateFormat: SimpleDateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    var  getSimpleTimeFormat: SimpleDateFormat =  SimpleDateFormat("HH:mm:ss")

    var simpleDateTimeFormatForView: SimpleDateFormat =  SimpleDateFormat("dd.MM.yyyy")


}