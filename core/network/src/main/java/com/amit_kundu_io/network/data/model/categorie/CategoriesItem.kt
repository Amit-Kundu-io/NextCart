package com.amit_kundu_io.network.data.model.categorie

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesItem(
    val name: String,
    val slug: String,
    val url: String
)