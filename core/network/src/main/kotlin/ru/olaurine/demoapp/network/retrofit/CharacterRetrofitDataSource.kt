package ru.olaurine.demoapp.network.retrofit

import ru.olaurine.demoapp.network.CharacterDataSource
import ru.olaurine.demoapp.network.model.CharacterRemote
import javax.inject.Inject

class CharacterRetrofitDataSource @Inject constructor(
    private val characterService: CharacterService
) : CharacterDataSource {

    override suspend fun getCharacterInfo(id: Int): CharacterRemote =
        characterService.getCharacterInfo(id)
}