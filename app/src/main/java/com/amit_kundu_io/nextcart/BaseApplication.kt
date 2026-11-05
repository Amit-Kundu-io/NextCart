package com.amit_kundu_io.nextcart

import android.app.Application
import com.amit_kundu_io.auth.di.AuthModule
import com.amit_kundu_io.database.dataStoreModule
import com.amit_kundu_io.network.di.getCoreNetworkModule
import com.amit_kundu_io.nextcart.root_navigation.bottom_navigation.BottomNavViewModel
import com.amit_kundu_io.nextcart.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * File: BaseApplication.kt
 * Created by Amit Kundu
 * Created on 04-Nov-26
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(
                AuthModule,
                rootDi,
                getCoreNetworkModule,
                dataStoreModule            )
        }
    }
}

val rootDi  = module {
    viewModelOf(::BottomNavViewModel)
    viewModelOf(::SplashViewModel)
}