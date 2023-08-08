package ru.stolexiy.cocktails.ui.cocktail.edit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
    ) { backStackEntry ->
        val cocktailId =
            backStackEntry.arguments!!.getLong(CocktailEditDialogDestination.COCKTAIL_ID_ARG)
        if (cocktailId != -1L)
            EditCocktailDialogScreen(cocktailId = cocktailId)
        else
            AddCocktailDialogScreen()
    }
}

object CocktailEditDialogDestination {
    val PATH = "cocktail/edit"
    val COCKTAIL_ID_ARG = "cocktailId"
}

@Composable
fun AddCocktailDialogScreen() {
    Text("add cocktail")
}

@Composable
fun EditCocktailDialogScreen(
    cocktailId: Long
) {
    Text("edit cocktail $cocktailId")
}
