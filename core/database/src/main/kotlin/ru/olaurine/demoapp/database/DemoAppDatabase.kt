package ru.olaurine.demoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.olaurine.demoapp.database.converters.IntListConverter
import ru.olaurine.demoapp.database.dao.CharacterDao
import ru.olaurine.demoapp.database.entities.CharacterEntity

@Database(
    entities = [
        CharacterEntity::class
    ], version = 1
)
@TypeConverters(IntListConverter::class)
abstract class DemoAppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "demoApp.db"
    }

    abstract fun characterDao(): CharacterDao
}