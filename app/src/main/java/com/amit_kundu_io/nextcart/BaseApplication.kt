package com.amit_kundu_io.nextcart

import android.app.Application
import com.amit_kundu_io.auth.di.AuthModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
            )
        }
    }
}