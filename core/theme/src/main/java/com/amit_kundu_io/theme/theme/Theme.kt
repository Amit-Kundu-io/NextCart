package com.amit_kundu_io.theme.theme

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme

import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
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
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}


@Composable
fun SetLightStatusBar() {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = true   // dark icons
    }
}



