package com.amit_kundu_io.nextcart.root_navigation.bottom_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amit_kundu_io.catalog.navigation.CatalogRoutes
import com.amit_kundu_io.catalog.navigation.catalogNavigation
import com.amit_kundu_io.home.presentation.navigation.HomeRoutes
import com.amit_kundu_io.home.presentation.navigation.homeNavigation
import com.amit_kundu_io.profile.presentation.navigation.profileNavigation


@Composable
fun BottomNavHost(
    navController: NavHostController,
    logOut: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = HomeRoutes.HomeGraph
    ) {
        homeNavigation(
            navController,
            onProductClick = {
                navController.navigate(CatalogRoutes.ProductDetails(it))
            }
        )
        catalogNavigation(navController)
        profileNavigation(navController)
    }
}
