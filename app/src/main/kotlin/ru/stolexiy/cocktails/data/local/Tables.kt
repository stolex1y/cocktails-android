package ru.stolexiy.cocktails.data.local

internal object Tables {
    object Cocktails {
        const val NAME = "cocktails"

        object Fields {
            const val ID = "cocktail_id"
            const val TITLE = "cocktail_title"
            const val DESCRIPTION = "cocktail_description"
            const val RECIPE = "cocktail_recipe"
            const val IMAGE = "cocktail_image"
            const val INGREDIENTS = "cocktail_ingredients"
        }
    }
}
