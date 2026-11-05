package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.theme.theme.NextCartTheme
import com.example.nextcart.data.SampleData

@Composable
fun FlashDealsRow(
    products: List<Product>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    onProductClick: (Product) -> Unit = {},
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(240.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyRow(
            modifier = modifier,
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(products, key = { it.id }) { product ->
                ProductCard(product, Modifier.width(170.dp)) { onProductClick(product) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FlashDealsRowPreview() {
    NextCartTheme() {
        FlashDealsRow(SampleData.flashDeals)
    }
}
