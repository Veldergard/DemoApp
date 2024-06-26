package ru.olaurine.demoapp.network.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import ru.olaurine.demoapp.network.model.CharacterResponse

interface CharacterService {

    @GET("character/{id}")
    suspend fun getCharacterInfo(
        @Path("id") id: Int
    ): CharacterResponse
}