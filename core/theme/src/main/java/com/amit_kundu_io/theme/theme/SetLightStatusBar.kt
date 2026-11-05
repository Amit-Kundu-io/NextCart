/**
 * File: SetLightStatusBar.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.theme.theme

import android.app.Activity
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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