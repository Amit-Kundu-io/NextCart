/**
 * File: catalogDi.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.di

import com.amit_kundu_io.catalog.product.ProductViewModel
import com.amit_kundu_io.catalog.product_details.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  catalogDi  = module{

    viewModelOf(::ProductViewModel)
    viewModelOf(::ProductDetailsViewModel)
}