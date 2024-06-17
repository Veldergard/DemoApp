package ru.olaurine.demoapp.network

import ru.olaurine.demoapp.network.model.CharacterRemote

interface CharacterDataSource {
    suspend fun getCharacterInfo(id: Int = 0): CharacterRemote
}