package ru.olaurine.demoapp.data.repository

import kotlinx.coroutines.flow.Flow
import ru.olaurine.demoapp.data.model.CharacterDto

interface CharacterRepository {

    suspend fun syncCharacter(characterId: Int)

    fun observeCharacter(characterId: Int): Flow<CharacterDto?>
}