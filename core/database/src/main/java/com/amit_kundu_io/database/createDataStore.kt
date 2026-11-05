/**
 * File: createDataStore.kt
 * Created by Amit Kundu
 * Created on 06-Oct-26
 */

package com.amit_kundu_io.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile

private const val DATASTORE_NAME = "my_data_store"


 fun createDataStore(context: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile(DATASTORE_NAME)
        }
    )
}