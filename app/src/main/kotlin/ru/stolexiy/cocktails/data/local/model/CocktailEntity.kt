package ru.stolexiy.cocktails.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.stolexiy.cocktails.data.local.Tables
import ru.stolexiy.cocktails.domain.model.DomainCocktail

@Entity(
    tableName = Tables.Cocktails.NAME
)
data class CocktailEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = Tables.Cocktails.Fields.ID) val id: Long,
    @ColumnInfo(name = Tables.Cocktails.Fields.TITLE) val title: String,
    @ColumnInfo(name = Tables.Cocktails.Fields.DESCRIPTION) val description: String?,
    @ColumnInfo(name = Tables.Cocktails.Fields.RECIPE) val recipe: String?,
    @ColumnInfo(name = Tables.Cocktails.Fields.IMAGE) val image: String?,
) {
    fun toDomain() = DomainCocktail(
        id = id,
        title = title,
        description = description,
        recipe = recipe,
        image = image,
    )
}

fun DomainCocktail.toEntity() = CocktailEntity(
    id = id,
    title = title,
    description = description,
    recipe = recipe,
    image = image,
)
