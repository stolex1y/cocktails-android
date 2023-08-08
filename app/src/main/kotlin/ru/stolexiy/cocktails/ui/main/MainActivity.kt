package ru.stolexiy.cocktails.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import dagger.hilt.android.AndroidEntryPoint
import ru.stolexiy.cocktails.ui.cocktail.details.addCocktailDetailsScreen
import ru.stolexiy.cocktails.ui.cocktail.edit.addCocktailEditScreen
import ru.stolexiy.cocktails.ui.cocktails.CocktailsDestination
import ru.stolexiy.cocktails.ui.cocktails.addCocktailsScreen
import ru.stolexiy.cocktails.ui.navigation.CocktailsNavController
import ru.stolexiy.cocktails.ui.navigation.rememberCocktailsNavController
import ru.stolexiy.cocktails.ui.theme.CocktailsTheme


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailsTheme {
                val cocktailsNavController = rememberCocktailsNavController()
                NavHost(
                    navController = cocktailsNavController.navController,
                    startDestination = CocktailsDestination.PATH
                ) {
                    navGraph(
                        navController = cocktailsNavController
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.navGraph(
    navController: CocktailsNavController
) {
    addCocktailsScreen(
        onNavigateToAddCocktailDialog = navController::navigateToAddCocktail,
        onNavigateToCocktailDetails = navController::navigateToCocktailDetails
    )
    addCocktailEditScreen(
        onNavigateUp = navController::navigateUp
    )
    addCocktailDetailsScreen(
        onNavigateUp = navController::navigateUp,
        onNavigateToEditDialog = navController::navigateToEditCocktail
    )
}
