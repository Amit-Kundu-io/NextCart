package com.amit_kundu_io.nextcart.root_navigation.bottom_navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BottomNavViewModel() : ViewModel() {

    private val _bottomNavigationItems = MutableStateFlow<List<BottomNavItem>>(emptyList())
    val bottomNavigationItems = _bottomNavigationItems.asStateFlow()


    init {
        viewModelScope.launch {

            _bottomNavigationItems.value =  listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Catalog,
                    BottomNavItem.Profile,
                )
            }
        }

}