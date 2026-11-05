/**
 * File: ProductDetails.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.theme.components.BottomBuyBar
import com.amit_kundu_io.theme.components.ErrorMessage
import com.amit_kundu_io.theme.components.InfoRow
import com.amit_kundu_io.theme.components.ProductImagePager
import com.amit_kundu_io.theme.components.RatingRow
import com.amit_kundu_io.theme.components.ReviewItem
import com.amit_kundu_io.theme.components.SectionHeader
import com.amit_kundu_io.theme.components.discountLabel
import com.amit_kundu_io.theme.theme.NextCartTheme
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@Composable
fun ProductDetailsScreenRoot(
    viewModel: ProductDetailsViewModel = koinViewModel (),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProductDetailsScreen(
        state = state,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    state: ProductDetailsState,
    onBack: () -> Unit,
    onRetry: () -> Unit = {},
    onAddToCart: (Product) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        bottomBar = {
            state.product?.let { product ->
                BottomBuyBar(
                    priceText = "$" + String.format(Locale.US, "%.2f", product.price ?: 0.0),
                    onAddToCart = { onAddToCart(product) },
                    enabled = (product.stock ?: 1) > 0,
                )
            }
        },
    ) { padding ->
        Box(Modifier
            .fillMaxSize()
            .padding(padding)) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                state.error != null -> ErrorMessage(
                    message = state.error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )

                state.product != null -> ProductDetailsContent(state.product)
            }
        }
    }
}

@Composable
private fun ProductDetailsContent(product: Product) {
    val images = product.images?.takeIf { it.isNotEmpty() }
        ?: listOfNotNull(product.thumbnail)

    Column(Modifier.verticalScroll(rememberScrollState())) {
        ProductImagePager(images)

        Column(Modifier.padding(16.dp)) {
            Text(
                product.title.orEmpty(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            Row(
                Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RatingRow(
                    rating = product.rating ?: 0.0,
                    suffix = product.discountLabel(),
                )
            }

            product.description?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }

            SectionHeader("Details", Modifier.padding(top = 24.dp, bottom = 4.dp))
            product.brand?.let { InfoRow("Brand", it) }
            product.category?.let { InfoRow("Category", it) }
            product.availabilityStatus?.let {
                InfoRow(
                    "Status",
                    "$it (${product.stock ?: 0} left)"
                )
            }
            product.shippingInformation?.let { InfoRow("Shipping", it) }
            product.warrantyInformation?.let { InfoRow("Warranty", it) }
            product.returnPolicy?.let { InfoRow("Returns", it) }

            val reviews = product.reviews.orEmpty()
            if (reviews.isNotEmpty()) {
                SectionHeader("Reviews", Modifier.padding(top = 24.dp, bottom = 4.dp))
                reviews.forEachIndexed { index, review ->
                    if (index > 0) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    ReviewItem(
                        name = review.reviewerName.orEmpty(),
                        rating = review.rating ?: 0,
                        comment = review.comment.orEmpty(),
                    )
                }
            }
        }
    }
}

