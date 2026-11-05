package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.theme.NextCartTheme

@Composable
fun RatingRow(
    rating: Double,
    modifier: Modifier = Modifier,
    suffix: String? = null,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.Star, null,
            Modifier.size(14.dp),
            tint = Color(0xFFFFB300), // star amber stays constant on any dynamic palette
        )
        Text(
            buildString {
                append(rating)
                if (suffix != null) append(" · $suffix")
            },
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingRowPreview() {
    NextCartTheme {
        RatingRow(4.94, Modifier.padding(16.dp), suffix = "7.17% OFF")
    }
}
