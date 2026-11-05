package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.theme.theme.NextCartTheme

@Composable
fun NextCartTopBar(
    modifier: Modifier = Modifier,
    onNotificationsClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "NextCart",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Row {
                IconButton(onNotificationsClick) {
                    Icon(Icons.Outlined.Notifications, "Notifications")
                }
                IconButton(onCartClick) {
                    Icon(Icons.Outlined.ShoppingCart, "Cart")
                }
            }
        }
        Text(
            "Welcome back! Happy shopping",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NextCartTopBarPreview() {
    NextCartTheme() { NextCartTopBar(Modifier.padding(16.dp)) }
}
