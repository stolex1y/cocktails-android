package ru.stolexiy.cocktails.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(30.dp),
) {
    Button(
        modifier = modifier,
        contentPadding = contentPadding,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = CocktailsTheme.colors.button),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = stringResource(id = R.string.add_cocktail)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddButtonPreview() {
    CocktailsTheme {
        AddButton(
            onClick = {}
        )
    }
}
