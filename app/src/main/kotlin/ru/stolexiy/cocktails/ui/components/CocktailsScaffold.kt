package ru.stolexiy.cocktails.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@Composable
fun CocktailsScaffold(
    onAddButtonClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        backgroundColor = CocktailsTheme.colors.background,
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AddButton(onClick = onAddButtonClick)
        },
        bottomBar = {
            CocktailsBottomBar()
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            content()
        }
    }
}

@Composable
@Preview
fun CocktailsScaffoldPreview() {
    CocktailsTheme {
        CocktailsScaffold(
            onAddButtonClick = {}
        ) {

        }
    }
}
