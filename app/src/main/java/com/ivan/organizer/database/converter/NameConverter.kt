package com.ivan.organizer.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivan.organizer.database.model.Name

class NameConverter {
    @TypeConverter
    fun stringToName(json: String): Name? {
        val gson = Gson()
        val type = object : TypeToken<Name>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun nameToString(name: Name): String {
        val gson = Gson()
        val type = object : TypeToken<Name>() {}.type
        return gson.toJson(name, type)
    }
}