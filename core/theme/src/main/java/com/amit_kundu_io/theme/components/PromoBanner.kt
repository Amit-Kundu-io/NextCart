package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.theme.NextCartTheme

@Composable
fun PromoBanner(
    modifier: Modifier = Modifier,
    tag: String = "SPECIAL OFFER",
    title: String = "Discover something new",
    headline: String = "Great deals await!",
    caption: String = "Explore our latest collection.",
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Column(Modifier.padding(16.dp)) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.18f),
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Text(
                    tag,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                )
            }
            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 12.dp),
            )
            Text(
                headline,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                caption,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PromoBannerPreview() {
    NextCartTheme{ PromoBanner(Modifier.padding(16.dp)) }
}
