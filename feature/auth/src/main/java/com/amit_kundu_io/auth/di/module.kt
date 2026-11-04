/**
 * File: module.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

package com.amit_kundu_io.auth.di

import com.amit_kundu_io.auth.data.AuthRepository
import com.amit_kundu_io.auth.data.AuthRepositoryImpl
import com.amit_kundu_io.auth.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val AuthModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl()
    }

    viewModelOf(::LoginViewModel)

}