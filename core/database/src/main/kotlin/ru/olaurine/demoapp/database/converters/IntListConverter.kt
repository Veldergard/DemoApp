package ru.olaurine.demoapp.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IntListConverter {
    @TypeConverter
    fun fromIntList(data: List<Int>?): String = Json.encodeToString(data)

    @TypeConverter
    fun toIntList(data: String): List<Int>? = Json.decodeFromString(data)
}