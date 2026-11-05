/**
 * File: Profile.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.profile.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amit_kundu_io.network.data.model.user.User
import com.amit_kundu_io.theme.components.ErrorMessage
import com.amit_kundu_io.theme.components.InfoRow
import com.amit_kundu_io.theme.components.ProfileHeader
import com.amit_kundu_io.theme.components.SectionHeader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state,
        onRetry = {
            viewModel.loadProfile()
        }
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onRetry: () -> Unit = {},
    onLogout: () -> Unit = {},
) {
    Surface(Modifier.fillMaxSize(), color = colorScheme.surface) {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                state.error != null -> ErrorMessage(
                    message = state.error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )

                state.user != null -> ProfileContent(state.user, onLogout)
            }
        }
    }
}

@Composable
private fun ProfileContent(user: User, onLogout: () -> Unit) {
    val address = user.address?.let {
        listOfNotNull(it.address, it.city, it.state, it.country).joinToString(", ")
    }.orEmpty()

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        ProfileHeader(
            name = "${user.firstName} ${user.lastName}",
            username = user.username,
            imageUrl = user.image,
        )

        SectionHeader("Personal info", Modifier.padding(bottom = 8.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = colorScheme.surfaceContainerHighest,
        ) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                InfoRow("Email", user.email, icon = Icons.Outlined.Email)
                user.phone?.let { InfoRow("Phone", it, icon = Icons.Outlined.Phone) }
                user.gender?.let {
                    InfoRow(
                        "Gender",
                        it.replaceFirstChar(Char::uppercase),
                        icon = Icons.Outlined.Person
                    )
                }
                user.age?.let { InfoRow("Age", "$it years", icon = Icons.Outlined.Cake) }
                if (address.isNotEmpty()) InfoRow(
                    "Address",
                    address,
                    icon = Icons.Outlined.LocationOn
                )
            }
        }

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
        ) {
            Text("Log out")
        }
    }
}

