package com.ivan.organizer.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivan.organizer.database.model.Translations

class TranslationsConverter {
    @TypeConverter
    fun stringToTranslations(json: String): Translations? {
        val gson = Gson()
        val type = object : TypeToken<Translations>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun translationsToString(translations: Translations): String {
        val gson = Gson()
        val type = object : TypeToken<Translations>() {}.type
        return gson.toJson(translations, type)
    }
}