package ru.stolexiy.cocktails.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.ui.components.AddButton
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@Composable
fun CocktailsBottomBar(
    modifier: Modifier = Modifier,
) {
    val shape = MaterialTheme.shapes.extraLarge.copy(
        bottomEnd = CornerSize(0.dp),
        bottomStart = CornerSize(0.dp)
    )
    BottomAppBar(
        modifier = modifier
            .defaultMinSize(minHeight = 60.dp)
            .navigationBarsPadding()
            .shadow(
                elevation = 16.dp,
                shape = shape,
                ambientColor = CocktailsTheme.colors.shadow,
                spotColor = CocktailsTheme.colors.shadow
            ),

        backgroundColor = CocktailsTheme.colors.bottomBar,
        cutoutShape = CircleShape,
        contentColor = CocktailsTheme.colors.darkText,
        elevation = 0.dp,
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomBarPreview() {
    CocktailsTheme {
        Scaffold(
            backgroundColor = CocktailsTheme.colors.background,
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                AddButton(onClick = {})
            },
            bottomBar = {
                CocktailsBottomBar()
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text("Content")
            }
        }
    }
}
