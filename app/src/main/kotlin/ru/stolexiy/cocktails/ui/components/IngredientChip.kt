package ru.stolexiy.cocktails.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientChip(
    ingredient: String,
    onRemoveIngredient: () -> Unit
) {
    FilterChip(
        border = FilterChipDefaults.filterChipBorder(
            borderColor = CocktailsTheme.colors.lightDarkText,
            borderWidth = 1.dp
        ),
        colors = FilterChipDefaults.filterChipColors(
        ),
        selected = false,
        onClick = {},
        label = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = ingredient,
                    style = MaterialTheme.typography.bodySmall,
                )
                Image(
                    modifier = Modifier.clickable { onRemoveIngredient() },
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = stringResource(
                        id = R.string.remove_ingredient
                    )
                )
            }
        },
        shape = MaterialTheme.shapes.large
    )
}

@Preview
@Composable
private fun IngredientChipPreview() {
    CocktailsTheme {
        IngredientChip(
            ingredient = "Ingredient",
            onRemoveIngredient = {}
        )
    }
}
