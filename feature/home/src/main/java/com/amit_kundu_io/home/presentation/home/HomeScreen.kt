/**
 * File: Home.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.home.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.components.BestSellerItem
import com.amit_kundu_io.theme.components.FlashDealsRow
import com.amit_kundu_io.theme.components.NextCartTopBar
import com.amit_kundu_io.theme.components.PromoBanner
import com.amit_kundu_io.theme.components.SectionHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    onProductClick: (id: Long) -> Unit
) {

    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        NextCartTopBar()

        Spacer(modifier = Modifier.height(10.dp))

        HomeScreen(

            state = state,
            onAction = viewModel::onAction,
            onProductClick = onProductClick

        )
    }
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onProductClick: (id: Long) -> Unit
) {

    LazyColumn() {
        item {
            PromoBanner()
        }
        item {
            Spacer(modifier = Modifier.height(15.dp))
            SectionHeader("Flash Deals")
            Spacer(modifier = Modifier.height(10.dp))

            FlashDealsRow(
                products = state.flashDeals,
                onProductClick = {
                    onProductClick(it.id)
                },
                isLoading = state.isLoading
            )
        }

        item {
            Spacer(modifier = Modifier.height(15.dp))
            SectionHeader("Best Sellers")
            Spacer(modifier = Modifier.height(10.dp))
        }
        if (state.isBestSellersLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            items(
                state.bestSellers,
                key = { it.id },
            ) {
                BestSellerItem(
                    it,
                    onClick = {
                        onProductClick(it.id)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))

            }
        }
    }
}
