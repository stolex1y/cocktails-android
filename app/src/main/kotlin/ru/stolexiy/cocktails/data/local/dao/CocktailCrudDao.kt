package ru.stolexiy.cocktails.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.stolexiy.cocktails.data.local.Tables
import ru.stolexiy.cocktails.data.local.model.CocktailEntity

private const val GET_ALL = "SELECT * FROM ${Tables.Cocktails.NAME}"
private const val GET = "$GET_ALL WHERE ${Tables.Cocktails.Fields.ID} = :id"
private const val DELETE_ALL = "DELETE FROM ${Tables.Cocktails.NAME}"

internal abstract class CocktailCrudDao {
    @Insert
    abstract suspend fun insert(vararg entities: CocktailEntity): List<Long>

    @Query(GET_ALL)
    abstract fun getAll(): Flow<List<CocktailEntity>>

    @Query(GET)
    abstract fun get(id: Long): Flow<CocktailEntity?>

    @Update
    abstract suspend fun update(vararg entities: CocktailEntity)

    @Delete
    abstract suspend fun delete(vararg entities: CocktailEntity): Int

    @Query(DELETE_ALL)
    abstract suspend fun deleteAll(): Int
}
