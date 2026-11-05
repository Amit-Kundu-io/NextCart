package com.amit_kundu_io.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.amit_kundu_io.theme.theme.NextCartTheme


/** Network image that shows a placeholder icon in @Preview (Coil can't load there). */
@Composable
fun ProductImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
) {
    if (LocalInspectionMode.current) {
        Box(modifier, contentAlignment = Alignment.Center) {
            Icon(
                Icons.Outlined.Image, contentDescription,
                Modifier.size(32.dp), tint = MaterialTheme.colorScheme.outline
            )
        }
    } else {
        AsyncImage(url, contentDescription, modifier, contentScale = contentScale)
    }
}

@Preview
@Composable
private fun ProductImagePreview() {
    NextCartTheme {
        ProductImage("", Modifier.size(96.dp).fillMaxSize())
    }
}
