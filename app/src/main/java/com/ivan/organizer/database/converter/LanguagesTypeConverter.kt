package com.ivan.organizer.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivan.organizer.database.model.Languages

class LanguagesTypeConverter {
    @TypeConverter
    fun stringToLanguages(json: String): Languages {
        val gson = Gson()
        val type = object : TypeToken<Languages>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun languagesToString(list: Languages): String {
        val gson = Gson()
        val type = object : TypeToken<Languages>() {}.type
        return gson.toJson(list, type)
    }
}