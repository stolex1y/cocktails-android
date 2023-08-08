package ru.stolexiy.cocktails.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes text: Int,
    enabled: Boolean = true,
) {
    Button(
        enabled = enabled,
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = CocktailsTheme.colors.button,
            contentColor = CocktailsTheme.colors.lightText,
            disabledContainerColor = CocktailsTheme.colors.lightDarkText,
            disabledContentColor = CocktailsTheme.colors.lightText,
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
fun TextButtonLight(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes text: Int,
    enabled: Boolean = true
) {
    Button(
        enabled = enabled,
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled)
                CocktailsTheme.colors.button
            else
                CocktailsTheme.colors.darkText
        ),
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = CocktailsTheme.colors.background,
            contentColor = CocktailsTheme.colors.button,
            disabledContentColor = CocktailsTheme.colors.lightDarkText
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TextButtonPreview() {
    CocktailsTheme {
        TextButton(enabled = false, onClick = {}, text = R.string.edit)
    }
}

@Composable
@Preview(showBackground = true)
private fun TextButtonLightPreview() {
    CocktailsTheme {
        TextButtonLight(enabled = false, onClick = {}, text = R.string.edit)
    }
}
