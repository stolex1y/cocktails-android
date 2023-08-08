package ru.stolexiy.cocktails.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stolexiy.cocktails.data.repository.CocktailCrudRepositoryImpl
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    fun cocktailsCrudRepository(i: CocktailCrudRepositoryImpl): CocktailCrudRepository
}
