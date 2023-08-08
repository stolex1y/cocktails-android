package ru.stolexiy.cocktails.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.stolexiy.cocktails.domain.model.DomainCocktail

interface CocktailCrudRepository {
    fun getAll(): Flow<Result<List<DomainCocktail>>>

    fun get(id: Long): Flow<Result<DomainCocktail?>>

    suspend fun update(vararg cocktail: DomainCocktail): Result<Unit>

    suspend fun delete(vararg id: Long): Result<Unit>

    suspend fun deleteAll(): Result<Unit>
}
