package com.example.todoapp.Database

import androidx.room.TypeConverter
import java.util.*

//this class to convert the date into format to understand it
class DateConverter {

    @TypeConverter // date in long format
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter //date in normal format
    fun toDate(time: Long): Date {
        return Date(time)
    }
}