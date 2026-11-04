/**
 * File: HomeNavigation.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.home.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


fun NavGraphBuilder.homeNavigation(navController: NavHostController) {
    navigation<HomeRoutes.HomeGraph>(startDestination = HomeRoutes.HomeRoute) {
        composable<HomeRoutes.HomeRoute>() {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Home")
            }
        }
    }
}