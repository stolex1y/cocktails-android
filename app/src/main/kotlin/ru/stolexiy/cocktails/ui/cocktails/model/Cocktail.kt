package ru.stolexiy.cocktails.ui.cocktails.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.stolexiy.cocktails.domain.model.DomainCocktail

@Parcelize
@Immutable
data class Cocktail(
    val id: Long,
    val title: String,
    val image: String?,
) : Parcelable

fun DomainCocktail.toCocktail() = Cocktail(
    id = id,
    title = title,
    image = image,
)
