package ru.stolexiy.cocktails.data.local.model

data class Ingredients(
    val values: List<String>
) {
    companion object {
        fun fromStrings(ingredients: List<String>) = Ingredients(
            values = ingredients
        )
    }
}
