package ru.olaurine.demoapp.data.repository

import kotlinx.coroutines.flow.Flow
import ru.olaurine.demoapp.data.model.CharacterDto

interface CharacterRepository {

    fun getCharacterDataFlow(characterId: Int?): Flow<CharacterDto?>

    suspend fun syncCharacter(characterId: Int)
}