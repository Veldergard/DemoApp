package ru.olaurine.demoapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CHARACTER_TABLE = "characters"

@Entity(tableName = CHARACTER_TABLE)
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originUrl: String,
    val locationName: String,
    val locationUrl: String,
    val imageUrl: String,
    val episodeIds: List<Int>,
    val created: String
)