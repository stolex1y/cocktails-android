package ru.stolexiy.cocktails.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CocktailsColors(
    val button: Color,
    val darkText: Color,
    val lightText: Color,
    val lightDarkText: Color,
    val errorText: Color,
    val bottomBar: Color,
)

private val theme_light_button = Color(0xFF4B97FF)
private val theme_light_dark_text = Color(0xFF313131)
private val theme_light_light_text = Color(0xFFFFFFFF)
private val theme_light_light_dark_text = Color(0xFF79747E)
private val theme_light_error_text = Color(0xFFFF0000)
private val theme_light_bottom_bar = Color(0xFFF6F8F9)

val LocalCocktailsColors = staticCompositionLocalOf {
    CocktailsColors(
        button = Color.Unspecified,
        darkText = Color.Unspecified,
        lightText = Color.Unspecified,
        lightDarkText = Color.Unspecified,
        errorText = Color.Unspecified,
        bottomBar = Color.Unspecified,
    )
}

val CocktailsLightColorScheme = CocktailsColors(
    button = theme_light_button,
    darkText = theme_light_dark_text,
    lightText = theme_light_light_text,
    lightDarkText = theme_light_light_dark_text,
    errorText = theme_light_error_text,
    bottomBar = theme_light_bottom_bar,
)
