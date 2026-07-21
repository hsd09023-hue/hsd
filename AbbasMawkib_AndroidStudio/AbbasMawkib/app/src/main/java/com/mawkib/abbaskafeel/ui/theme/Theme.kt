package com.mawkib.abbaskafeel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = Gold,
    onPrimary = DeepBlack,
    secondary = DarkGreenLight,
    onSecondary = OffWhite,
    tertiary = DeepRed,
    onTertiary = OffWhite,
    background = DeepBlack,
    onBackground = OffWhite,
    surface = SurfaceBlack,
    onSurface = OffWhite,
    surfaceVariant = DarkGreen,
    onSurfaceVariant = OffWhite,
    outline = MutedGray
)

private val LightColors = lightColorScheme(
    primary = DeepRed,
    onPrimary = OffWhite,
    secondary = DarkGreen,
    onSecondary = OffWhite,
    tertiary = Gold,
    onTertiary = DeepBlack,
    background = OffWhite,
    onBackground = DeepBlack,
    surface = SurfaceLight,
    onSurface = DeepBlack,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = DeepBlack,
    outline = MutedGray
)

@Composable
fun AbbasMawkibTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
