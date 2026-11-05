/**
 * File: ProfileNavigation.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.amit_kundu_io.profile.presentation.profile.ProfileScreenRoot


fun NavGraphBuilder.profileNavigation(navController: NavHostController) {
    navigation<ProfileRoutes.ProfileGraph>(startDestination = ProfileRoutes.ProfileRoute) {
        composable<ProfileRoutes.ProfileRoute>() {
            ProfileScreenRoot()
        }
    }
}