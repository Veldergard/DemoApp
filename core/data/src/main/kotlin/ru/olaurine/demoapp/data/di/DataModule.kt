package ru.olaurine.demoapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.olaurine.demoapp.data.repository.CharacterRepository
import ru.olaurine.demoapp.data.repository.CharacterRepositoryImpl
import ru.olaurine.demoapp.database.dao.CharacterDao
import ru.olaurine.demoapp.network.CharacterDataSource

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    fun provideCharacterRepository(
        characterDataSource: CharacterDataSource,
        characterDao: CharacterDao
    ): CharacterRepository = CharacterRepositoryImpl(characterDataSource, characterDao)
}