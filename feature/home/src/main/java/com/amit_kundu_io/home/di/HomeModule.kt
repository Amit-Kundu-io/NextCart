/**
 * File: HomeModule.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.home.di

import com.amit_kundu_io.home.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  HomeModule = module {
    viewModelOf(::HomeViewModel)
}