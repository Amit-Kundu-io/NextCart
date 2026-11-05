package com.amit_kundu_io.theme.components

import com.amit_kundu_io.network.data.model.product.Product
import java.util.Locale

fun Product.priceLabel(): String = "$" + String.format(Locale.US, "%.2f", price)

/** "7.17% OFF" or null when there is no discount. */
fun Product.discountLabel(): String? = discountPercentage.takeIf { it > 0.0 }?.let { String.format(Locale.US, "%.2f%% OFF", it) }
