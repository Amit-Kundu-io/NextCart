/**
 * File: CategoryChipsRow.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.network.data.model.categorie.CategoriesItem
import com.amit_kundu_io.theme.theme.NextCartTheme

@Composable
fun CategoryChipsRow(
    categories: List<CategoriesItem>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(key = "all") {
            CategoryChip(
                label = "All",
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
            )
        }
        items(categories, key = { it.slug }) { category ->
            CategoryChip(
                label = category.name,
                selected = selectedCategory == category.slug,
                onClick = { onCategorySelected(category.slug) },
            )
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipsRowPreview() {
    NextCartTheme {
        CategoryChipsRow(
            categories = emptyList(), // add sample CategoriesItem(...) here
            selectedCategory = null,
            onCategorySelected = {},
        )
    }
}