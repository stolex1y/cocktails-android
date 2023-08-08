package ru.stolexiy.cocktails.ui.cocktail.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.addCocktailDetailsScreen(
    onNavigateToEditDialog: (cocktailId: Long) -> Unit,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = "${CocktailDetailsDestination.PATH}/{${CocktailDetailsDestination.COCKTAIL_ID_ARG}}",
        arguments = listOf(navArgument(CocktailDetailsDestination.COCKTAIL_ID_ARG) {
            type = NavType.LongType
        })
    ) { backStackEntry ->
        CocktailDetailsScreen(
            cocktailId = backStackEntry.arguments!!.getLong(
                CocktailDetailsDestination.COCKTAIL_ID_ARG
            )
        )
    }
}

object CocktailDetailsDestination {
    val PATH = "cocktail"
    val COCKTAIL_ID_ARG = "cocktailId"
}

@Composable
fun CocktailDetailsScreen(cocktailId: Long) {
    Text("cocktail $cocktailId")
}
