package ru.stolexiy.cocktails.data.local

import androidx.room.TypeConverter
import ru.stolexiy.cocktails.data.local.model.Ingredients

object Converters {
    @TypeConverter
    fun ingredientsToString(ingredients: Ingredients): String =
        ingredients.values.joinToString("\n")

    @TypeConverter
    fun ingredientsFromString(ingredientsStr: String) = Ingredients(
        values = ingredientsStr.split("\n")
    )
}
