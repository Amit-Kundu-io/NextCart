/**
 * File: Product.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.catalog.product.components.ProductGrid
import com.amit_kundu_io.theme.components.CategoryChipsRow
import com.amit_kundu_io.theme.components.ErrorMessage
import com.amit_kundu_io.theme.components.ProductSearchBar
import com.amit_kundu_io.theme.theme.NextCartTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductRoute(
    onProductClick: (id: Long) -> Unit = {},
) {
    val viewModel: ProductViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductScreen(
        state = state,
        onAction = viewModel::onAction,
        onProductClick = onProductClick,
    )
}

@Composable
private fun ProductScreen(
    state: ProductState,
    onAction: (ProductAction) -> Unit,
    onProductClick: (id: Long) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(Modifier.statusBarsPadding()) {

            Text(
                "Products",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp),
            )

            ProductSearchBar(
                query = state.searchQuery,
                onQueryChange = { onAction(ProductAction.SearchChanged(it)) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            CategoryChipsRow(
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = { onAction(ProductAction.CategorySelected(it)) },
                modifier = Modifier.padding(top = 12.dp),
            )


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(3.dp)
            ) {
                if (state.isProductLoading && state.products.isNotEmpty()) {
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                }
            }

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when {
                    state.isProductLoading && state.products.isEmpty() -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    state.productsError != null && state.products.isEmpty() -> {
                        ErrorMessage(
                            message = state.productsError,
                            modifier = Modifier.align(Alignment.Center),
                            onRetry = { onAction(ProductAction.Refresh) },
                        )
                    }

                    state.products.isEmpty() -> {
                        ErrorMessage(
                            message = "No products found",
                            icon = Icons.Outlined.SearchOff,
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }

                    else -> {
                        ProductGrid(
                            products = state.products,
                            onLoadMore = { onAction(ProductAction.LoadMore) },
                            onProductClick = { onProductClick(it.id) },
                            modifier = Modifier.fillMaxSize(),
                            resetKey = state.selectedCategory,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NextCartTheme {
        ProductScreen(
            state = ProductState(),
            onAction = {}
        )
    }
}