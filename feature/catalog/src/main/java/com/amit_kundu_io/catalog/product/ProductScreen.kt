/**
 * File: Product.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.catalog.product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.theme.NextCartTheme

@Composable
fun ProductScreenRoot(
    viewModel: ProductViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProductScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ProductScreen(
    state: ProductState,
    onAction: (ProductAction) -> Unit,
) {

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