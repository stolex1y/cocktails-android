package ru.stolexiy.cocktails.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.stolexiy.cocktails.common.CoroutineDispatcherNames
import ru.stolexiy.cocktails.common.FlowExtensions.mapList
import ru.stolexiy.cocktails.common.FlowExtensions.mapToResult
import ru.stolexiy.cocktails.common.Mappers.mapToArray
import ru.stolexiy.cocktails.data.local.dao.CocktailCrudDao
import ru.stolexiy.cocktails.data.local.model.CocktailEntity
import ru.stolexiy.cocktails.data.local.model.toEntity
import ru.stolexiy.cocktails.domain.model.DomainCocktail
import ru.stolexiy.cocktails.domain.repository.CocktailCrudRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class CocktailCrudRepositoryImpl @Inject constructor(
    private val localDao: CocktailCrudDao,
    @Named(CoroutineDispatcherNames.IO_DISPATCHER) private val dispatcher: CoroutineDispatcher
) : CocktailCrudRepository {
    override suspend fun add(vararg cocktails: DomainCocktail): Result<List<Long>> = runCatching {
        withContext(dispatcher) {
            Timber.d("add cocktails (count: ${cocktails.size})")
            localDao.insert(*cocktails.mapToArray(DomainCocktail::toEntity))
        }
    }

    override fun getAll(): Flow<Result<List<DomainCocktail>>> {
        return localDao.getAll()
            .distinctUntilChanged()
            .mapList(CocktailEntity::toDomain)
            .flowOn(dispatcher)
            .onEach { Timber.d("get all cocktails (count: ${it.size})") }
            .mapToResult()
    }

    override fun get(id: Long): Flow<Result<DomainCocktail?>> {
        return localDao.get(id)
            .distinctUntilChanged()
            .map { it?.toDomain() }
            .flowOn(dispatcher)
            .onEach { Timber.d("get cocktail by id: $id") }
            .mapToResult()
    }

    override suspend fun update(vararg cocktails: DomainCocktail): Result<Unit> = runCatching {
        withContext(dispatcher) {
            Timber.d("update cocktails with ids: ${cocktails.map { it.id }.joinToString(", ")}")
            localDao.update(*cocktails.mapToArray(DomainCocktail::toEntity))
        }
    }

    override suspend fun delete(vararg ids: Long): Result<Int> = runCatching {
        withContext(dispatcher) {
            Timber.d("delete cocktails with ids: ${ids.joinToString(", ")}")
            ids.map { localDao.getOnce(it) }
                .filterNotNull()
                .map { async { localDao.delete(it) } }
                .sumOf { it.await() }
        }
    }

    override suspend fun deleteAll(): Result<Int> = runCatching {
        withContext(dispatcher) {
            Timber.d("delete all cocktails")
            localDao.deleteAll()
        }
    }
}
