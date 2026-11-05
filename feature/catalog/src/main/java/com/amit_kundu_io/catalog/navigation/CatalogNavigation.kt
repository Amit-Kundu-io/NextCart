/**
 * File: CatalogNavigation.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.amit_kundu_io.catalog.product.ProductRoute
import com.amit_kundu_io.catalog.product_details.ProductDetailsScreenRoot


fun NavGraphBuilder.catalogNavigation(navController: NavHostController) {
    navigation<CatalogRoutes.CatalogGraph>(startDestination = CatalogRoutes.CatalogRoute) {
        composable<CatalogRoutes.CatalogRoute>() {
            ProductRoute(
                onProductClick = {
                    navController.navigate(CatalogRoutes.ProductDetails(it))
                }
            )
        }
        composable<CatalogRoutes.ProductDetails>() {
            ProductDetailsScreenRoot(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}