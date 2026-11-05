package com.amit_kundu_io.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext().applicationContext)
    }

    single {
        MyDataStore(get())
    }
}