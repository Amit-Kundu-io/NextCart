package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.theme.theme.NextCartTheme
import com.example.nextcart.data.SampleData

/** Vertical product card used in the Flash Deals row. */
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(modifier) {
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            modifier = Modifier.aspectRatio(1f),
        ) {
            ProductImage(
                url = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize().padding(8.dp),
            )
        }
        Text(
            product.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 10.dp),
        )
        Text(
            product.priceLabel(),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 6.dp),
        )
        RatingRow(
            rating = product.rating,
            suffix = product.discountLabel(),
            modifier = Modifier.padding(top = 6.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    NextCartTheme {
        ProductCard(SampleData.flashDeals.first(), Modifier.width(170.dp).padding(16.dp))
    }
}
