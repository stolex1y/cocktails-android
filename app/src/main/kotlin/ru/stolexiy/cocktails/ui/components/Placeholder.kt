package ru.stolexiy.cocktails.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@Composable
fun Placeholder(
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int,
) {
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.large),
        color = CocktailsTheme.colors.placeholderBackground,
        contentColor = CocktailsTheme.colors.fieldLabel,
    ) {
        Image(
            contentScale = ContentScale.None,
            painter = painterResource(id = R.drawable.photo),
            contentDescription = stringResource(id = contentDescription)
        )
    }
}

@Preview
@Composable
private fun PlaceholderPreview() {
    CocktailsTheme {
        Placeholder(
            modifier = Modifier.defaultMinSize(minWidth = 200.dp, minHeight = 200.dp),
            contentDescription = R.string.cocktail_image
        )
    }
}
