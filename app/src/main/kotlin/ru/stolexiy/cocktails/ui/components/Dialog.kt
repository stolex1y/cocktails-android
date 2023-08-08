package ru.stolexiy.cocktails.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

class DialogState private constructor() {
    var open: Boolean by mutableStateOf(false)
        private set

    fun close() {
        open = false
    }

    fun open() {
        open = true
    }

    companion object {
        private val saver = listSaver<DialogState, Any?>(
            save = {
                listOf(it.open)
            },
            restore = {
                DialogState().apply {
                    open = it[0] as Boolean
                }
            }
        )

        @Composable
        fun rememberDialogState(): DialogState = rememberSaveable(saver = saver) {
            DialogState()
        }
    }
}

@Composable
fun CocktailDialog(
    modifier: Modifier = Modifier,
    dialogState: DialogState = DialogState.rememberDialogState(),
    content: @Composable () -> Unit,
) {
    if (dialogState.open) {
        Dialog(onDismissRequest = {
            dialogState.close()
        }) {
            Surface(
                modifier = modifier,
                shape = MaterialTheme.shapes.large,
                color = CocktailsTheme.colors.background,
                contentColor = CocktailsTheme.colors.darkText
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    content()
                }
            }
        }
    }
}
