package ru.stolexiy.cocktails.ui.cocktail.edit.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.listSaver
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.domain.model.DomainCocktail
import ru.stolexiy.cocktails.ui.util.validation.Conditions
import ru.stolexiy.cocktails.ui.util.validation.ValidatedEntity
import ru.stolexiy.cocktails.ui.util.validation.ValidatedProperty

@Stable
class Cocktail(
    val id: Long = 0,
    title: String = "",
    description: String? = null,
    recipe: String? = null,
    image: String? = null,
    ingredients: List<String> = emptyList(),
) : ValidatedEntity() {
    var description: ValidatedProperty<String?> = addValidatedProperty(
        initialValue = description
    )

    var recipe: ValidatedProperty<String?> = addValidatedProperty(
        initialValue = recipe
    )

    var image: ValidatedProperty<String?> = addValidatedProperty(
        initialValue = image
    )

    val title: ValidatedProperty<String> = addValidatedProperty(
        initialValue = title,
        condition = Conditions.RequiredField(
            R.string.add_title
        )
    )

    val ingredients: ValidatedProperty<List<String>> = addValidatedProperty(
        initialValue = ingredients,
        condition = Conditions.NotEmptyList(
            R.string.add_ingredients
        )
    )

    fun toDomain() = DomainCocktail(
        id = id,
        title = title.value,
        description = description.value,
        recipe = recipe.value,
        image = image.value,
        ingredients = ingredients.value
    )

    fun removeIngredient(ingredient: String) {
        ingredients.set(ingredients.value.toMutableList().apply { remove(ingredient) })
    }

    fun addIngredient(ingredient: String) {
        ingredients.set(ingredients.value.toMutableList().apply { add(ingredient) })
    }

    companion object {
        val saver = listSaver<Cocktail, Any?>(
            save = {
                listOf(
                    it.id,
                    it.title.value,
                    it.description.value,
                    it.image.value,
                    it.recipe.value,
                    it.ingredients.value.joinToString("\n")
                )
            },
            restore = {
                Cocktail(
                    id = it[0] as Long,
                    title = it[1] as String,
                    description = it[2] as String?,
                    image = it[3] as String?,
                    recipe = it[4] as String?,
                    ingredients = (it[5] as String).split("\n")
                )
            }
        )
    }
}

fun DomainCocktail.toCocktail() = Cocktail(
    id = id,
    title = title,
    description = description,
    recipe = recipe,
    image = image,
    ingredients = ingredients
)
