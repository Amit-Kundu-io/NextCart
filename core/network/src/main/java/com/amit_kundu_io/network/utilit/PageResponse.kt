/**
 * File: PageResponse.kt
 * Created by Amit Kundu
 * Created on 25-Oct-26
 */

package com.amit_kundu_io.network.utilit

import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
)