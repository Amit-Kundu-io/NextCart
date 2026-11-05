/**
 * File: Profile.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.profile.presentation.profile

import androidx.compose.runtime.Immutable
import com.amit_kundu_io.network.data.model.user.User

@Immutable
data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
)
