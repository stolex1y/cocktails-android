package ru.stolexiy.cocktails.ui.cocktail.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.collectLatest
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.cocktail.CocktailViewModel
import ru.stolexiy.cocktails.ui.cocktail.edit.model.Cocktail
import ru.stolexiy.cocktails.ui.cocktail.edit.model.toCocktail
import ru.stolexiy.cocktails.ui.components.AddButton
import ru.stolexiy.cocktails.ui.components.CocktailsOutlinedTextField
import ru.stolexiy.cocktails.ui.components.CocktailsOutlinedTextFieldNullable
import ru.stolexiy.cocktails.ui.components.IngredientChip
import ru.stolexiy.cocktails.ui.components.Placeholder
import ru.stolexiy.cocktails.ui.components.TextButton
import ru.stolexiy.cocktails.ui.components.TextButtonLight
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme
import ru.stolexiy.cocktails.ui.util.validation.ValidatedProperty

fun NavGraphBuilder.addCocktailEditScreen(
    onNavigateUp: () -> Unit,
) {
    composable(
        route = "${CocktailEditDialogDestination.PATH}?" +
                "${CocktailEditDialogDestination.COCKTAIL_ID_ARG}={${CocktailEditDialogDestination.COCKTAIL_ID_ARG}}",
        arguments = listOf(navArgument(CocktailEditDialogDestination.COCKTAIL_ID_ARG) {
            type = NavType.LongType
            defaultValue = -1
        })
    ) { navBackStackEntry ->
        val editingCocktailId =
            navBackStackEntry.arguments?.getLong(CocktailEditDialogDestination.COCKTAIL_ID_ARG)
                ?.takeIf { it > -1 }
        EditCocktailDialogScreen(
            editingCocktailId = editingCocktailId,
            onCancelEditing = onNavigateUp
        )
    }
}

object CocktailEditDialogDestination {
    val PATH = "cocktail/edit"
    val COCKTAIL_ID_ARG = "cocktailId"
}

@Composable
fun EditCocktailDialogScreen(
    viewModel: CocktailViewModel = hiltViewModel(),
    editingCocktailId: Long? = null,
    onCancelEditing: () -> Unit
) {
    val editingCocktail: Cocktail? by viewModel.editingCocktailAsState()

    reloadData(viewModel)

    EditCocktailForm(
        editingCocktail = editingCocktail,
        onSaveCocktail = {
            val cocktail = it.toDomain()
            if (editingCocktailId != null)
                viewModel.updateCocktail(cocktail)
            else
                viewModel.addCocktail(cocktail)
        },
        onCancel = onCancelEditing
    )
}

@Composable
fun EditCocktailForm(
    editingCocktail: Cocktail? = null,
    onSaveCocktail: (Cocktail) -> Unit,
    onCancel: () -> Unit,
) {
    val cocktail = rememberSaveable(
        saver = Cocktail.saver
    ) {
        editingCocktail ?: Cocktail()
    }
    Column(
        modifier = Modifier
            .padding(
                top = 40.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 26.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        val image: String? by cocktail.image.asState
        CocktailImage(
            modifier = Modifier
                .padding(horizontal = 72.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            image = image
        )
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CocktailsOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                validatedProperty = cocktail.title,
                singleLine = true,
                label = R.string.title,
                required = true
            )
            CocktailsOutlinedTextFieldNullable(
                modifier = Modifier.fillMaxWidth(),
                validatedProperty = cocktail.description,
                singleLine = false,
                label = R.string.description,
                required = false,
                minLines = 7,
                shape = MaterialTheme.shapes.medium
            )
            CocktailIngredients(
                onRemoveIngredient = { cocktail.removeIngredient(it) },
                ingredientsProperty = cocktail.ingredients,
                onAddIngredient = { TODO() }
            )
            CocktailsOutlinedTextFieldNullable(
                modifier = Modifier.fillMaxWidth(),
                validatedProperty = cocktail.recipe,
                singleLine = false,
                label = R.string.recipe,
                required = false,
                minLines = 7,
                shape = MaterialTheme.shapes.medium
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(
                onClick = {
                    if (cocktail.isValid)
                        onSaveCocktail(cocktail)
                },
                text = R.string.save,
                modifier = Modifier.fillMaxWidth()
            )
            TextButtonLight(
                onClick = onCancel,
                text = R.string.cancel,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CocktailImage(
    modifier: Modifier = Modifier,
    image: String?
) {
    if (image == null)
        Placeholder(modifier = modifier, contentDescription = R.string.cocktail_image)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CocktailIngredients(
    onRemoveIngredient: (String) -> Unit,
    ingredientsProperty: ValidatedProperty<List<String>>,
    onAddIngredient: () -> Unit,
) {
    val ingredients by ingredientsProperty.asState
    val validationResult by ingredientsProperty.validationResultAsState
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.bodySmall,
            color = if (validationResult.isNotValid)
                CocktailsTheme.colors.errorText
            else
                CocktailsTheme.colors.lightDarkText
        )
        FlowRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ingredients.forEach { ingredient ->
                IngredientChip(
                    ingredient = ingredient,
                    onRemoveIngredient = { onRemoveIngredient(ingredient) }
                )
            }
            val buttonPadding = if (ingredients.isEmpty())
                PaddingValues(horizontal = 16.dp)
            else
                PaddingValues(horizontal = 0.dp)
            AddButton(
                contentPadding = PaddingValues(7.dp),
                modifier = Modifier
                    .padding(buttonPadding)
                    .height(24.dp)
                    .width(24.dp),
                onClick = onAddIngredient
            )
        }
        if (validationResult.isNotValid) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = validationResult.errorMessageRes!!),
                style = MaterialTheme.typography.bodySmall,
                color = CocktailsTheme.colors.errorText
            )
        }
    }
}

@Composable
private fun CocktailViewModel.editingCocktailAsState(): State<Cocktail?> =
    produceState<Cocktail?>(
        initialValue = null
    ) {
        data.collectLatest {
            value = it.cocktail?.toCocktail()
        }
    }

@SuppressLint("ComposableNaming")
@Composable
private fun reloadData(viewModel: CocktailViewModel) {
    LaunchedEffect(viewModel) {
        viewModel.reloadData()
    }
}

@Composable
@Preview(showBackground = true)
private fun EditCocktailFormPreview() {
    CocktailsTheme {
        EditCocktailForm(
            editingCocktail = cocktailPreviewData(),
            onSaveCocktail = {},
            onCancel = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AddCocktailFormPreview() {
    CocktailsTheme {
        EditCocktailForm(
            editingCocktail = null,
            onSaveCocktail = {},
            onCancel = {}
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
