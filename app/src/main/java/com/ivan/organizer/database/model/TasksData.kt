package com.ivan.organizer.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Tasks")
@Parcelize
class TasksData (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: Name?,
    val tld: List<String>?,
    val currency: List<String>?,
    val callingCode: List<String>?,
    val capital: String?,
    val region: String?,
    val subregion: String?,
    val nativeLanguage: String?,
    val languages: Languages?,
    val translations: Translations?,
    val demonym: String?,
    val borders: List<String>?,
    val area: Double?
) : Parcelable