/**
 * File: ProductGrid.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.theme.components.ProductCard
import com.amit_kundu_io.theme.theme.NextCartTheme
import com.example.nextcart.data.SampleData

@Composable
fun ProductGrid(
    products: List<Product>,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    resetKey: String? = null,
    onProductClick: (Product) -> Unit = {},
) {
    val gridState = rememberLazyGridState()

    // scroll back to the first item whenever the key changes
    LaunchedEffect(resetKey) {
        gridState.scrollToItem(0)
    }

    val nearEnd by remember {
        derivedStateOf {
            val info = gridState.layoutInfo
            val lastVisible = info.visibleItemsInfo.lastOrNull()?.index ?: 0
            info.totalItemsCount > 0 && lastVisible >= info.totalItemsCount - 4
        }
    }

    LaunchedEffect(nearEnd, products.size) {
        if (nearEnd) onLoadMore()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(product, Modifier.fillMaxWidth()) { onProductClick(product) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductGridPreview() {
    NextCartTheme { ProductGrid(products = SampleData.flashDeals, onLoadMore = {}) }
}