package ru.stolexiy.cocktails.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.stolexiy.cocktails.ui.cocktail.details.CocktailDetailsDestination
import ru.stolexiy.cocktails.ui.cocktail.edit.CocktailEditDialogDestination

@Composable
fun rememberCocktailsNavController(
    navController: NavHostController = rememberNavController()
): CocktailsNavController = remember(navController) {
    CocktailsNavController(navController)
}

@Stable
class CocktailsNavController(
    val navController: NavHostController
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateToRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToCocktailDetails(cocktailId: Long) {
        navController.navigate("${CocktailDetailsDestination.PATH}/$cocktailId")
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToAddCocktail() {
        navController.navigate(CocktailEditDialogDestination.PATH)
    }

    fun navigateToEditCocktail(cocktailId: Long) {
        navController.navigate(
            "${CocktailEditDialogDestination.PATH}?" +
                    "${CocktailEditDialogDestination.COCKTAIL_ID_ARG}=$cocktailId"
        )
    }
}
