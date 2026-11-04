package com.amit_kundu_io.nextcart.root_navigation.bottom_navigation

import com.amit_kundu_io.theme.R

enum class BottomNavDestination {
    HOME,
    CATALOG,
    PROFILE

}

sealed class BottomNavItem(
    val destination: BottomNavDestination,
    val label: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
) {
    data object Home : BottomNavItem(
        destination = BottomNavDestination.HOME,
        label = "Workshops",
        selectedIcon = R.drawable.baseline_home_filled_24,
        unSelectedIcon = R.drawable.outline_home_24
    )

    data object Catalog : BottomNavItem(
        destination = BottomNavDestination.CATALOG,
        label = "Catalog",
        selectedIcon = R.drawable.baseline_shopping_cart_24,
        unSelectedIcon = R.drawable.outline_shopping_cart_24
    )

    data object Profile : BottomNavItem(
        destination = BottomNavDestination.CATALOG,
        label = "Profile",
        selectedIcon = R.drawable.baseline_person_24,
        unSelectedIcon = R.drawable.outline_person_24
    )
}



