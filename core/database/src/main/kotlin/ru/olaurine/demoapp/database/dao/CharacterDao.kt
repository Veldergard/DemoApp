package ru.olaurine.demoapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.olaurine.demoapp.database.entities.CHARACTER_TABLE
import ru.olaurine.demoapp.database.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM $CHARACTER_TABLE WHERE id = :characterId")
    fun observeCharacter(characterId: Int): Flow<CharacterEntity?>
}