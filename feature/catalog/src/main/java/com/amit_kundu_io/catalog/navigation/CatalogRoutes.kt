/**
 * File: CatalogRoutes.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.navigation

import kotlinx.serialization.Serializable


@Serializable
object CatalogRoutes {
    @Serializable
    object CatalogGraph

    @Serializable
    object CatalogRoute
 @Serializable
 data class ProductDetails(val id : Long)

}