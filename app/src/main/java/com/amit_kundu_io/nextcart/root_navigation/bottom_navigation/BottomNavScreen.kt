package com.amit_kundu_io.nextcart.root_navigation.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amit_kundu_io.catalog.navigation.CatalogRoutes
import com.amit_kundu_io.home.presentation.navigation.HomeRoutes
import com.amit_kundu_io.nextcart.root_navigation.bottom_navigation.BottomNavDestination.CATALOG
import com.amit_kundu_io.nextcart.root_navigation.bottom_navigation.BottomNavDestination.HOME
import com.amit_kundu_io.nextcart.root_navigation.bottom_navigation.BottomNavDestination.PROFILE
import com.amit_kundu_io.profile.presentation.navigation.ProfileRoutes
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BottomNavScreen(
    navigateToLoginScreen: () -> Unit,
    logOut: () -> Unit,
    viewModel: BottomNavViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    val items by viewModel.bottomNavigationItems.collectAsStateWithLifecycle()




    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
                label = "BottomNavigationAnimation"
            ) {
                BottomNavigationContainer(
                    navController = navController,
                    items = items
                )
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            BottomNavHost(
                navController = navController,
                logOut = logOut,
            )
        }
    }
}

@Composable
private fun BottomNavigationContainer(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        AppBottomNav(
            navController = navController,
            items = items
        )
    }
}

@Composable
fun AppBottomNav(
    navController: NavHostController,
    items: List<BottomNavItem>,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    CustomBottomBar(
        items = items,
        currentDestination = backStackEntry?.destination,
        onValueChange = { item ->
            when (item.destination) {
                HOME ->{
                    navController.navigate(HomeRoutes.HomeRoute)
                }
                CATALOG -> {
                    navController.navigate(CatalogRoutes.CatalogRoute)
                }
                PROFILE -> {
                    navController.navigate(ProfileRoutes.ProfileRoute)
                }
            }
        }
    )
}

@Composable
fun CustomBottomBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onValueChange: (BottomNavItem) -> Unit
) {
    val selectedColor = MaterialTheme.colorScheme.primary
    val interactionSource = remember { MutableInteractionSource() }

    Box() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 5.dp,
                    start = 8.dp,
                    end = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEach { screen ->
                val isSelected = currentDestination
                    ?.hierarchy
                    ?.any { destination ->
                        when (screen.destination) {
                            HOME -> destination.hasRoute<HomeRoutes.HomeRoute>()
                           CATALOG ->  destination.hasRoute<CatalogRoutes.CatalogRoute>()
                            PROFILE ->  destination.hasRoute<ProfileRoutes.ProfileRoute>()
                        }
                    } == true

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onValueChange(screen)
                        }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isSelected) {
                                screen.selectedIcon
                            } else {
                                screen.unSelectedIcon
                            }
                        ),
                        contentDescription = screen.label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = screen.label,
                        color = if (isSelected) selectedColor else Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Medium
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(6.dp))

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(
                                        topStart = 8.dp,
                                        topEnd = 8.dp
                                    )
                                )
                                .background(selectedColor)
                        )
                    }
                }
            }
        }
    }
}
