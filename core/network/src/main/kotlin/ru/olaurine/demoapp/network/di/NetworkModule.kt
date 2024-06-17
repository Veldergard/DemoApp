package ru.olaurine.demoapp.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.olaurine.demoapp.network.CharacterDataSource
import ru.olaurine.demoapp.network.retrofit.CharacterService
import ru.olaurine.demoapp.network.retrofit.CharacterRetrofitDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://rickandmortyapi.com/api/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=UTF-8".toMediaType()
            )
        )
        .build()

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideDataSource(characterService: CharacterService): CharacterDataSource {
        return CharacterRetrofitDataSource(characterService)
    }
}