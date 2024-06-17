package ru.olaurine.demoapp.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import ru.olaurine.demoapp.database.DemoAppDatabase
import ru.olaurine.demoapp.database.dao.CharacterDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DemoAppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = DemoAppDatabase::class.java,
            name = DemoAppDatabase.DB_NAME
        )
            .setQueryExecutor(Dispatchers.IO.asExecutor())
            .setTransactionExecutor(Dispatchers.IO.asExecutor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: DemoAppDatabase): CharacterDao {
        return database.characterDao()
    }
}