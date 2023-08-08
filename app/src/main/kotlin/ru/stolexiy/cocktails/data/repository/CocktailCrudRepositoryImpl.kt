package ru.stolexiy.cocktails.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import ru.stolexiy.cocktails.common.CoroutineDispatcherNames
import ru.stolexiy.cocktails.data.local.dao.CocktailCrudDao
import ru.stolexiy.cocktails.domain.model.DomainCocktail
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class CocktailCrudRepositoryImpl @Inject constructor(
    private val localDao: CocktailCrudDao,
    @Named(CoroutineDispatcherNames.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) : CocktailCrudRepository {
    override fun getAll(): Flow<Result<List<DomainCocktail>>> {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Flow<Result<DomainCocktail?>> {
        TODO("Not yet implemented")
    }

    override suspend fun update(vararg cocktail: DomainCocktail): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg id: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Unit> {
        TODO("Not yet implemented")
    }
}
