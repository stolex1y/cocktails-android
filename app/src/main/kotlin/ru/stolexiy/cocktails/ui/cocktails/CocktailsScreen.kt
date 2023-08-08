package ru.stolexiy.cocktails.ui.cocktails

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.collectLatest
import ru.stolexiy.cocktails.R
import ru.stolexiy.cocktails.ui.cocktails.model.Cocktail
import ru.stolexiy.cocktails.ui.components.AddButton
import ru.stolexiy.cocktails.ui.components.CocktailsScaffold
import ru.stolexiy.cocktails.ui.components.Placeholder
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme

fun NavGraphBuilder.addCocktailsScreen(
    onNavigateToCocktailDetails: (cocktailId: Long) -> Unit,
    onNavigateToAddCocktailDialog: () -> Unit,
) {
    composable(
        route = CocktailsDestination.PATH
    ) {
        CocktailsScreen(
            onNavigateToCocktailDetails = onNavigateToCocktailDetails,
            onNavigateToAddCocktailDialog = onNavigateToAddCocktailDialog,
        )
    }
}

object CocktailsDestination {
    val PATH = "cocktails"
}

@Composable
private fun CocktailsScreen(
    onNavigateToCocktailDetails: (cocktailId: Long) -> Unit,
    onNavigateToAddCocktailDialog: () -> Unit,
    viewModel: CocktailsViewModel = hiltViewModel()
) {
    val cocktails: List<Cocktail> by viewModel.cocktailsAsState()
    val screenState: CocktailsViewModel.State by viewModel.state.collectAsState()

    reloadData(viewModel)

    if (cocktails.isEmpty() && screenState == CocktailsViewModel.State.Loaded)
        EmptyContent(onAddCocktail = onNavigateToAddCocktailDialog)
    else
        CocktailsList(
            cocktails = cocktails,
            onAddCocktail = onNavigateToAddCocktailDialog,
            onCocktailClick = onNavigateToCocktailDetails,
        )
}

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.my_cocktails),
        style = MaterialTheme.typography.displaySmall,
        color = CocktailsTheme.colors.darkText,
    )
}

@Composable
private fun EmptyContent(
    onAddCocktail: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = 24.dp,
                start = 38.dp,
                end = 38.dp,
                bottom = 24.dp
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.aspectRatio(1f),
            painter = painterResource(id = R.drawable.summer_holidays),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(15.dp))
        Title()
        Spacer(modifier = Modifier.height(32.dp))
        AddCocktailHint(onAddCocktail = onAddCocktail)
    }
}

@Composable
private fun CocktailsList(
    cocktails: List<Cocktail>,
    onAddCocktail: () -> Unit,
    onCocktailClick: (cocktailId: Long) -> Unit,
) {
    CocktailsScaffold(
        onAddButtonClick = onAddCocktail
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Title()
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .wrapContentWidth(),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items = cocktails, key = { it.id }) { cocktail ->
                    CocktailCard(
                        onCocktailClick = { onCocktailClick(cocktail.id) },
                        cocktail = cocktail
                    )
                }
            }
        }
    }
}

@Composable
private fun AddCocktailHint(
    modifier: Modifier = Modifier,
    onAddCocktail: () -> Unit,
) {
    Column(
        modifier = modifier.widthIn(max = 130.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.add_first_cocktail_hint),
            style = MaterialTheme.typography.titleMedium,
            color = CocktailsTheme.colors.lightDarkText
        )
        Image(
            painter = painterResource(id = R.drawable.arrow_down),
            contentDescription = stringResource(id = R.string.arrow_down)
        )
        AddButton(onClick = onAddCocktail)
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun CocktailCard(
    onCocktailClick: () -> Unit,
    cocktail: Cocktail
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clipToBounds()
            .clickable(onClick = onCocktailClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            contentColor = CocktailsTheme.colors.lightText
        )
    ) {
        val textMeasurer = rememberTextMeasurer()
        val textStyle = MaterialTheme.typography.titleLarge
        val textColor = if (cocktail.image == null)
            CocktailsTheme.colors.darkText
        else
            CocktailsTheme.colors.lightText
        val imageModifier = Modifier
            .fillMaxSize()
            .drawTextOnContent(
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                textColor = textColor,
                text = cocktail.title
            )
            .aspectRatio(1f)
        if (cocktail.image == null)
            Placeholder(
                modifier = imageModifier,
                contentDescription = R.string.cocktail_image
            )
        // TODO image from files
//        else
        /*Image(
            bitmap = ImageBitmap(),
            contentDescription = stringResource(id = R.string.cocktail_image)
        )*/
    }
}

@OptIn(ExperimentalTextApi::class)
private fun Modifier.drawTextOnContent(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    textColor: Color,
    text: String,
): Modifier {
    return this.drawWithCache {
        val measuredText = textMeasurer.measure(
            text = text,
            style = textStyle
        )
        val offsetX = (size.width - measuredText.size.width) / 2f
        val offsetY = (size.height - 34.dp.toPx() - measuredText.size.height)
        onDrawWithContent {
            drawContent()
            drawText(
                textLayoutResult = measuredText,
                color = textColor,
                topLeft = Offset(offsetX, offsetY)
            )
        }
    }
}

@Composable
private fun CocktailsViewModel.cocktailsAsState() = produceState(
    initialValue = emptyList<Cocktail>()
) {
    data.collectLatest {
        value = it.cocktails
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun reloadData(viewModel: CocktailsViewModel) {
    LaunchedEffect(viewModel) {
        viewModel.reloadData()
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun CocktailsListPreview() {
    CocktailsTheme {
        CocktailsList(
            onAddCocktail = {},
            cocktails = previewCocktails(),
            onCocktailClick = {},
        )
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun EmptyContentPreview() {
    CocktailsTheme {
        EmptyContent(
            onAddCocktail = {}
        )
    }
}

private fun previewCocktails(): List<Cocktail> {
    return listOf(
        Cocktail(
            id = 0,
            title = "Cocktail 1",
            image = null,
        ),
        Cocktail(
            id = 1,
            title = "Cocktail 2",
            image = null,
        ),
        Cocktail(
            id = 2,
            title = "Cocktail 3",
            image = null,
        ),
        Cocktail(
            id = 3,
            title = "Cocktail 4",
            image = null,
        )
    )
}
