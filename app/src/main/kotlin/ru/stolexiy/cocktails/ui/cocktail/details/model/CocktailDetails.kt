package ru.stolexiy.cocktails.ui.cocktail.details.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.stolexiy.cocktails.domain.model.DomainCocktail

@Parcelize
data class CocktailDetails(
    val id: Long,
    val title: String,
    val description: String?,
    val recipe: String?,
    val image: String?,
    val ingredients: List<String>,
) : Parcelable

fun DomainCocktail.toCocktailDetails() = CocktailDetails(
    id = id,
    title = title,
    description = description,
    recipe = recipe,
    image = image,
    ingredients = ingredients,
)