package com.atruskova.itunesapitesttask.data.database

import androidx.room.TypeConverter
import com.atruskova.itunesapitesttask.data.DataConverter
import java.util.*

object DatabaseTypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<String>? {
        return data?.let {
            it.split(",")}
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<String>?): String? {
        return ints?.joinToString(",")
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: Date?) : String  = DataConverter.dateToString(date)

    @TypeConverter
    @JvmStatic
    fun stringToDate(stringDate: String) : Date?  = DataConverter.stringToDate(stringDate)



}