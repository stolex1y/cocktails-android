package ru.stolexiy.cocktails.ui.cocktail.details

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import ru.stolexiy.cocktails.ui.cocktail.details.model.CocktailDetails
import ru.stolexiy.cocktails.ui.cocktail.details.model.toCocktailDetails
import ru.stolexiy.cocktails.ui.components.CocktailDialog
import ru.stolexiy.cocktails.ui.components.DialogState
import ru.stolexiy.cocktails.ui.components.Placeholder
import ru.stolexiy.cocktails.ui.components.TextButton
import ru.stolexiy.cocktails.ui.components.TextButtonLight
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

fun NavGraphBuilder.addCocktailDetailsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToEditDialog: (cocktailId: Long) -> Unit,
) {
    composable(
        route = "${CocktailDetailsDestination.PATH}/{${CocktailDetailsDestination.COCKTAIL_ID_ARG}}",
        arguments = listOf(navArgument(CocktailDetailsDestination.COCKTAIL_ID_ARG) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        val cocktailId = backStackEntry.arguments!!.getLong(
            CocktailDetailsDestination.COCKTAIL_ID_ARG
        )
        CocktailDetailsScreen(
            onNavigateUp = onNavigateUp,
            onEditCocktail = { onNavigateToEditDialog(cocktailId) },
        )
    }
}

object CocktailDetailsDestination {
    val PATH = "cocktail"
    val COCKTAIL_ID_ARG = "cocktailId"
}

@Composable
private fun CocktailDetailsScreen(
    onNavigateUp: () -> Unit,
    onEditCocktail: () -> Unit,
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val cocktail: CocktailDetails? by viewModel.cocktailDetailsAsState()
    val deletingDialogState = DialogState.rememberDialogState()

    reloadData(viewModel)

    AnimatedVisibility(visible = cocktail != null) {
        cocktail?.let {
            CocktailDetails(
                onEditCocktail = onEditCocktail,
                cocktail = it,
                onDeleteCocktail = {
                    deletingDialogState.open()
                }
            )
        }
    }
    SubmitDeletingDialog(
        dialogState = deletingDialogState,
        cocktailTitle = cocktail?.title ?: "",
        onSubmit = {
            viewModel.deleteCocktail()
            onNavigateUp()
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedVisibilityScope.CocktailDetails(
    onEditCocktail: () -> Unit,
    cocktail: CocktailDetails,
    onDeleteCocktail: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy((-17).dp)
    ) {
        val density = LocalDensity.current
        CocktailImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 330.dp)
                .aspectRatio(1f)
                .animateEnterExit(
                    enter = slideInVertically {
                        with(density) { -it.dp.roundToPx() }
                    } + fadeIn(),
                    exit = slideOutVertically {
                        with(density) { -it.dp.roundToPx() }
                    } + fadeOut()
                ),
            image = cocktail.image
        )
        CocktailInfo(
            modifier = Modifier
                .animateEnterExit(
                    enter = slideInVertically {
                        with(density) { 2 * it.dp.roundToPx() }
                    },
                    exit = slideOutVertically {
                        with(density) { 2 * it.dp.roundToPx() }
                    }
                ),
            cocktail = cocktail,
            onEditCocktail = onEditCocktail,
            onDeleteCocktail = onDeleteCocktail
        )
    }
}

@Composable
private fun CocktailImage(
    modifier: Modifier = Modifier,
    image: String?
) {
    if (image == null) {
        Placeholder(
            modifier = modifier,
            contentDescription = R.string.cocktail_image,
            shape = RectangleShape
        )
    }
}

@Composable
private fun CocktailInfo(
    onEditCocktail: () -> Unit,
    onDeleteCocktail: () -> Unit,
    modifier: Modifier = Modifier,
    cocktail: CocktailDetails
) {
    Surface(
        modifier = modifier.fillMaxHeight(),
        color = CocktailsTheme.colors.background,
        contentColor = CocktailsTheme.colors.darkText,
        shape = MaterialTheme.shapes.large.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 28.dp, bottom = 24.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Text(
                    text = cocktail.title,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                if (cocktail.description?.isNotBlank() == true) {
                    Text(
                        text = cocktail.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
                CocktailIngredients(ingredients = cocktail.ingredients)
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                if (cocktail.recipe?.isNotBlank() == true) {
                    CocktailRecipe(recipe = cocktail.recipe)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onEditCocktail,
                text = R.string.edit
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            TextButtonLight(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onDeleteCocktail,
                text = R.string.delete
            )
        }
    }
}

@Composable
private fun CocktailIngredients(
    ingredients: List<String>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ingredients.forEachIndexed { index, ingredient ->
            Text(
                text = ingredient,
                style = MaterialTheme.typography.bodyLarge
            )
            if (index < ingredients.size - 1) {
                Text(
                    text = "—",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun CocktailRecipe(recipe: String) {
    Text(
        text = stringResource(id = R.string.recipe) + ":"
    )
    Text(
        text = recipe,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun SubmitDeletingDialog(
    cocktailTitle: String,
    dialogState: DialogState,
    onSubmit: () -> Unit,
) {
    CocktailDialog(
        modifier = Modifier
            .fillMaxWidth(),
        dialogState = dialogState
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.do_you_wanna_delete) + " \"$cocktailTitle\"?",
                style = MaterialTheme.typography.headlineLarge
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onSubmit()
                        dialogState.close()
                    },
                    text = R.string.yes
                )
                TextButtonLight(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        dialogState.close()
                    },
                    text = R.string.no
                )
            }
        }
    }
}

@Composable
private fun CocktailViewModel.cocktailDetailsAsState(): State<CocktailDetails?> =
    produceState<CocktailDetails?>(
        initialValue = null
    ) {
        data.collectLatest {
            value = it.cocktail?.toCocktailDetails()
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
private fun CocktailDetailsPreview() {
    CocktailsTheme {
        val state = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }
        AnimatedVisibility(visibleState = state) {
            CocktailDetails(
                cocktail = cocktailDetailsPreviewData(),
                onEditCocktail = {},
                onDeleteCocktail = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SubmitDeleteDialogPreview() {
    CocktailsTheme {
        SubmitDeletingDialog(
            dialogState = DialogState.rememberDialogState().apply { open() },
            onSubmit = {},
            cocktailTitle = "Cocktail"
        )
    }
}

private fun cocktailDetailsPreviewData() = CocktailDetails(
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
