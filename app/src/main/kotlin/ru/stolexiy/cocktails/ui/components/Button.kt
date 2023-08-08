package ru.stolexiy.cocktails.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes text: Int
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = CocktailsTheme.colors.button,
            contentColor = CocktailsTheme.colors.lightText,
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
        TextButton(onClick = {}, text = R.string.edit)
    }
}
