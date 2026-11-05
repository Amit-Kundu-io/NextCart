/**
 * File: Home.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.home.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.theme.components.NextCartTopBar
import com.amit_kundu_io.theme.components.PromoBanner
import com.amit_kundu_io.theme.theme.NextCartTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
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
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {

    LazyColumn() {
        item {
            PromoBanner()
        }

    }

}

@Preview
@Composable
private fun Preview() {
    NextCartTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}