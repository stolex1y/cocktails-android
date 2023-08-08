package ru.stolexiy.cocktails.data.local.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.stolexiy.cocktails.data.local.LocalDatabase
import ru.stolexiy.cocktails.data.local.dao.CocktailCrudDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LocalDatasourceModule {

    companion object {
        @Provides
        @Singleton
        fun localDatabase(@ApplicationContext context: Context): LocalDatabase {
            return LocalDatabase.getInstance(context)
        }

        @Provides
        @Singleton
        fun localCocktailCrudDao(localDatabase: LocalDatabase): CocktailCrudDao {
            return localDatabase.cocktailCrudDao()
        }
    }
}
