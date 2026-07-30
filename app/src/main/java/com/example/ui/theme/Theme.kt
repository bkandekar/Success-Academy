package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = TextOnGold,
    primaryContainer = NavyContainer,
    onPrimaryContainer = TextOnNavy,
    secondary = GoldLight,
    onSecondary = TextOnGold,
    secondaryContainer = NavyLight,
    onSecondaryContainer = TextOnNavy,
    background = NavyDark,
    onBackground = TextOnNavy,
    surface = NavyPrimary,
    onSurface = TextOnNavy,
    surfaceVariant = NavyLight,
    onSurfaceVariant = GoldLight
)

private val LightColorScheme = lightColorScheme(
    primary = NavyPrimary,
    onPrimary = TextOnNavy,
    primaryContainer = NavyLight,
    onPrimaryContainer = TextOnNavy,
    secondary = GoldAccent,
    onSecondary = TextOnGold,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = TextPrimaryDark,
    background = BackgroundLight,
    onBackground = TextPrimaryDark,
    surface = SurfaceWhite,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondaryDark
)

@Composable
fun SuccessAcademyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
