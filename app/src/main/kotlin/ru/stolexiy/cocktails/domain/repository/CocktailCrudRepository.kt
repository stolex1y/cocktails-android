package ru.stolexiy.cocktails.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.stolexiy.cocktails.domain.model.DomainCocktail

interface CocktailCrudRepository {
    suspend fun add(vararg cocktails: DomainCocktail): Result<List<Long>>

    fun getAll(): Flow<Result<List<DomainCocktail>>>

    fun get(id: Long): Flow<Result<DomainCocktail?>>

    suspend fun update(vararg cocktails: DomainCocktail): Result<Unit>

    suspend fun delete(vararg ids: Long): Result<Int>

    suspend fun deleteAll(): Result<Int>
}
