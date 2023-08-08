package ru.stolexiy.cocktails.ui.cocktail.edit.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.util.validation.Conditions
import ru.stolexiy.cocktails.ui.util.validation.ValidatedEntity
import ru.stolexiy.cocktails.ui.util.validation.ValidatedProperty

class Ingredient(
    title: String = ""
) : ValidatedEntity() {
    val title: ValidatedProperty<String> = addValidatedProperty(
        initialValue = title,
        condition = Conditions.RequiredField(R.string.add_title)
    )

    companion object {
        val saver = listSaver<MutableState<Ingredient>, Any?>(
            save = {
                listOf(
                    it.value.title.value,
                )
            },
            restore = {
                mutableStateOf(
                    Ingredient(
                        title = it[0] as String,
                    )
                )
            }
        )
    }
}
