package com.example.prototipo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

// Los temas que van a predominar en el aplicativo, aquí se define los colores principales y los tipos de fuentes que se
// van a usar.
private val DarkColorScheme = darkColors(
    primary = Green200,
    primaryVariant = Green700,
    secondary = Teal200,
)

private val LightColorScheme = lightColors(
    primary = Green200,
    primaryVariant = Green700,
    secondary = Teal200,
)

@Composable
fun PrototipoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}