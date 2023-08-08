package ru.stolexiy.cocktails.ui.cocktails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

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
fun CocktailsScreen(
    onNavigateToCocktailDetails: (cocktailId: Long) -> Unit,
    onNavigateToAddCocktailDialog: () -> Unit,
) {
    Column {
        Text("cocktails")
        Button(onClick = { onNavigateToAddCocktailDialog() }) {
            Text("add")
        }
        Button(onClick = { onNavigateToCocktailDetails(1) }) {
            Text("details")
        }
    }
}


