package com.example.hnotes.core.designsystem.theme

import android.os.Build
import android.util.Log
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

val LightColorScheme = lightColorScheme(
    primary = theme_light_primary,
    onPrimary = theme_light_onPrimary,
    primaryContainer = theme_light_primaryContainer,
    onPrimaryContainer = theme_light_onPrimaryContainer,
    inversePrimary = theme_light_inversePrimary,
    secondary = theme_light_secondary,
    onSecondary = theme_light_onSecondary,
    secondaryContainer = theme_light_secondaryContainer,
    onSecondaryContainer = theme_light_onSecondaryContainer,
    tertiary = theme_light_tertiary,
    onTertiary = theme_light_onTertiary,
    tertiaryContainer = theme_light_tertiaryContainer,
    onTertiaryContainer = theme_light_onTertiaryContainer,
    background = theme_light_background,
    onBackground = theme_light_onBackground,
    surface = theme_light_surface,
    onSurface = theme_light_onSurface,
    surfaceVariant = theme_light_surfaceVariant,
    onSurfaceVariant = theme_light_onSurfaceVariant,
    surfaceTint = theme_light_surfaceTint,
    inverseSurface = theme_light_inverseSurface,
    inverseOnSurface = theme_light_inverseOnSurface,
    error = theme_light_error,
    onError = theme_light_onError,
    errorContainer = theme_light_errorContainer,
    onErrorContainer = theme_light_onErrorContainer,
    outline = theme_light_outline,
    outlineVariant = theme_light_outlineVariant,
    scrim = theme_light_scrim
)

val DarkColorScheme = darkColorScheme(
    primary = theme_dark_primary,
    onPrimary = theme_dark_onPrimary,
    primaryContainer = theme_dark_primaryContainer,
    onPrimaryContainer = theme_dark_onPrimaryContainer,
    inversePrimary = theme_dark_inversePrimary,
    secondary = theme_dark_secondary,
    onSecondary = theme_dark_onSecondary,
    secondaryContainer = theme_dark_secondaryContainer,
    onSecondaryContainer = theme_dark_onSecondaryContainer,
    tertiary = theme_dark_tertiary,
    onTertiary = theme_dark_onTertiary,
    tertiaryContainer = theme_dark_tertiaryContainer,
    onTertiaryContainer = theme_dark_onTertiaryContainer,
    background = theme_dark_background,
    onBackground = theme_dark_onBackground,
    surface = theme_dark_surface,
    onSurface = theme_dark_onSurface,
    surfaceVariant = theme_dark_surfaceVariant,
    onSurfaceVariant = theme_dark_onSurfaceVariant,
    surfaceTint = theme_dark_surfaceTint,
    inverseSurface = theme_dark_inverseSurface,
    inverseOnSurface = theme_dark_inverseOnSurface,
    error = theme_dark_error,
    onError = theme_dark_onError,
    errorContainer = theme_dark_errorContainer,
    onErrorContainer = theme_dark_onErrorContainer,
    outline = theme_dark_outline,
    outlineVariant = theme_dark_outlineVariant,
    scrim = theme_dark_scrim
)

@Composable
fun HNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    enableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {

    // Color scheme
    val colorScheme = when {
        enableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context = context)
            else
                dynamicLightColorScheme(context = context)
        }
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }

    LaunchedEffect(key1 = darkTheme, key2 = enableDynamicTheming) {
    }

    // Gradient colors
    val emptyGradientColors =
        GradientColors(container = colorScheme.surfaceColorAtElevation(elevation = 2.dp))
    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface,
        bottom = colorScheme.primaryContainer,
        container = colorScheme.surface,
    )
    val gradientColors = when {
        !enableDynamicTheming && supportsDynamicTheming() -> emptyGradientColors
        else -> defaultGradientColors
    }

    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )
    val backgroundTheme = defaultBackgroundTheme

    // TintTheme
    val tintTheme =
        if (!enableDynamicTheming && supportsDynamicTheming()) TintTheme(iconTint = colorScheme.primary)
        else TintTheme()

    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {

        MaterialTheme(
            colorScheme = colorScheme,
            typography = HNotesTypography,
            content = content
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
