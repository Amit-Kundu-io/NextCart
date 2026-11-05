package com.amit_kundu_io.theme.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.colorResource
import com.amit_kundu_io.theme.R


@Composable
fun NextCartTheme(
    content: @Composable () -> Unit
) {
    val colors = lightColorScheme(
        primary = colorResource(R.color.brand_primary),
        secondary = colorResource(R.color.brand_secondary),
        surface = colorResource(R.color.brand_surface),
        background = colorResource(R.color.brand_surface),
        onPrimary = colorResource(R.color.brand_on_primary)
    )

    SetLightStatusBar()

    MaterialTheme(
        colorScheme = brandColorScheme(),
        typography = Typography,
        content = content
    )
}


/** Full M3 scheme generated from the 4 brand colors (light, matching your XML theme). */
@Composable
private fun brandColorScheme(): ColorScheme {
    val primary = colorResource(R.color.brand_primary)
    val secondary = colorResource(R.color.brand_secondary)
    val surface = colorResource(R.color.brand_surface)
    val onPrimary = colorResource(R.color.brand_on_primary)

    val onSurface = Color(0xFF1C1B1F)
    val dark = Color.Black

    return lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = lerp(surface, primary, 0.14f),
        onPrimaryContainer = lerp(primary, dark, 0.45f),

        secondary = secondary,
        onSecondary = Color.White,
        secondaryContainer = lerp(surface, secondary, 0.20f),
        onSecondaryContainer = lerp(secondary, dark, 0.55f),

        tertiary = secondary,
        tertiaryContainer = lerp(surface, secondary, 0.20f),
        onTertiaryContainer = lerp(secondary, dark, 0.55f),

        background = surface,
        onBackground = onSurface,
        surface = surface,
        onSurface = onSurface,

        // brand-tinted neutrals: image tiles, dividers, secondary text
        surfaceVariant = lerp(surface, primary, 0.10f),
        onSurfaceVariant = lerp(onSurface, primary, 0.30f),
        surfaceContainerLowest = surface,
        surfaceContainerLow = lerp(surface, primary, 0.03f),
        surfaceContainer = lerp(surface, primary, 0.05f),
        surfaceContainerHigh = lerp(surface, primary, 0.07f),
        surfaceContainerHighest = lerp(surface, primary, 0.09f),
        outline = lerp(onSurface, primary, 0.45f).copy(alpha = 0.6f),
        outlineVariant = lerp(surface, primary, 0.20f),
    )
}






