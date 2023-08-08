package ru.stolexiy.cocktails.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.cocktail.edit.model.Cocktail
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme
import ru.stolexiy.cocktails.ui.util.validation.ValidatedProperty
import ru.stolexiy.cocktails.ui.util.validation.ValidationResult

@Composable
fun CocktailsOutlinedTextFieldNullable(
    modifier: Modifier = Modifier,
    validatedProperty: ValidatedProperty<String?>,
    singleLine: Boolean = true,
    @StringRes label: Int,
    required: Boolean,
    minLines: Int = 1,
    shape: Shape = MaterialTheme.shapes.large
) {
    val value by validatedProperty.asState
    val validationResult by validatedProperty.validationResultAsState
    CocktailsOutlinedTextField(
        shape = shape,
        minLines = minLines,
        modifier = modifier,
        label = label,
        singleLine = singleLine,
        required = required,
        onValueChange = { validatedProperty.set(it) },
        value = value ?: "",
        validationResult = validationResult
    )
}

@Composable
fun CocktailsOutlinedTextField(
    modifier: Modifier = Modifier,
    validatedProperty: ValidatedProperty<String>,
    singleLine: Boolean = true,
    @StringRes label: Int,
    required: Boolean,
    minLines: Int = 1,
    shape: Shape = MaterialTheme.shapes.large
) {
    val value by validatedProperty.asState
    val validationResult by validatedProperty.validationResultAsState
    CocktailsOutlinedTextField(
        shape = shape,
        minLines = minLines,
        modifier = modifier,
        label = label,
        singleLine = singleLine,
        required = required,
        onValueChange = { validatedProperty.set(it) },
        value = value,
        validationResult = validationResult
    )
}

@Composable
private fun CocktailsOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult,
    singleLine: Boolean = true,
    @StringRes label: Int,
    required: Boolean,
    minLines: Int = 1,
    shape: Shape = MaterialTheme.shapes.large
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            minLines = minLines,
            value = value,
            onValueChange = { newValue: String ->
                onValueChange(newValue)
            },
            isError = validationResult.isNotValid,
            singleLine = singleLine,
            label = {
                Text(
                    text = stringResource(id = label),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CocktailsTheme.colors.darkText,
                errorBorderColor = CocktailsTheme.colors.errorText,
                errorCursorColor = CocktailsTheme.colors.errorText,
                errorTextColor = CocktailsTheme.colors.errorText,
                errorLabelColor = CocktailsTheme.colors.errorText,
                focusedTextColor = CocktailsTheme.colors.darkText,
                unfocusedTextColor = CocktailsTheme.colors.lightDarkText,
                cursorColor = CocktailsTheme.colors.darkText,
                unfocusedBorderColor = CocktailsTheme.colors.lightDarkText,
                unfocusedLabelColor = CocktailsTheme.colors.lightDarkText,
                focusedLabelColor = CocktailsTheme.colors.lightDarkText,
                selectionColors = TextSelectionColors(
                    backgroundColor = CocktailsTheme.colors.placeholderBackground,
                    handleColor = CocktailsTheme.colors.darkText
                ),
            ),
            shape = shape,
            textStyle = MaterialTheme.typography.bodyLarge,
        )

        val helpText = if (validationResult.isNotValid && validationResult.errorMessageRes != null)
            stringResource(id = validationResult.errorMessageRes!!)
        else if (required)
            stringResource(id = R.string.required_field)
        else
            stringResource(id = R.string.optional_field)
        val helpTextColor = if (validationResult.isNotValid)
            CocktailsTheme.colors.errorText
        else
            CocktailsTheme.colors.lightDarkText
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = helpText,
            style = MaterialTheme.typography.bodySmall,
            color = helpTextColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OutlinedTextFieldPreview() {
    CocktailsTheme {
        val cocktail = cocktailPreviewData()
        CocktailsOutlinedTextField(
            validatedProperty = cocktail.title,
            label = R.string.title,
            required = true
        )
    }
}

private fun cocktailPreviewData() = Cocktail(
    id = 0,
    title = "Cocktail",
    description = """
        To make this homemade pink lemonade, simply combine all the ingredients in a pitcher.
    """.trimIndent(),
    ingredients = """
        9 cups water
        2 cups white sugar
        2 cups fresh lemon juice
        1 cup cranberry juice, chilled
        ice as needed
    """.trimIndent().split("\n"),
    recipe = "Mix them all",
    image = null
)
