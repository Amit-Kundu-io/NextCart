/**
 * File: ProfileModel.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.profile.presentation.di

import com.amit_kundu_io.profile.presentation.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  ProfileModel = module {
    viewModelOf(::ProfileViewModel)
}