package com.smartdevices.petpal.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun medicalInfoToString(medicalInfo: MedicalInfo): String {
        return Gson().toJson(medicalInfo)
    }

    @TypeConverter
    fun stringToMedicalInfo(medicalInfoString: String): MedicalInfo {
        return Gson().fromJson(medicalInfoString, MedicalInfo::class.java)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return Gson().toJson(value) // Converts List<String> to a JSON string
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType) // Converts the JSON string back to List<String>
    }

    @TypeConverter
    fun fromEventList(events: List<Event>?): String {
        return Gson().toJson(events) // Convert the list to JSON
    }

    @TypeConverter
    fun toEventList(eventsString: String?): List<Event> {
        return if (eventsString.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<Event>>() {}.type
            Gson().fromJson(eventsString, type) // Convert JSON back to a list
        }
    }
}
