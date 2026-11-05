/**
 * File: MyDataStore.kt
 * Created by Amit Kundu
 * Created on 06-Oct-26
 */

package com.amit_kundu_io.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class MyDataStore(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val USER_ID = longPreferencesKey("userId")
        private val USER_ID_TYPE = intPreferencesKey("userTypeId")
        private val TOKEN = stringPreferencesKey("token")
        private val TOKEN_EXPIRED_AT = longPreferencesKey("tokenExpiredAt")
        private val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        private val REFRESH_TOKEN_EXPIRED_AT = longPreferencesKey("refreshTokenExpiredAt")

        //user
        private val EMAIL = stringPreferencesKey("email")
        private val NAME = stringPreferencesKey("name")
        private val ORG_ID = stringPreferencesKey("organisationId")
        private val DIAL_CODE = stringPreferencesKey("ddil")
        private val DESIGNATION = stringPreferencesKey("designation")
        private val PH_NUMBER = stringPreferencesKey("phoneNumber")
        private val USER_IMAGE_LINK = stringPreferencesKey("userImageLink")

        private val subscribedCoursesKey = stringSetPreferencesKey("subscribed_course_ids")
    }

    //----------------------------------------------------------------
    //Auth

    val userId = dataStore.data.map { preferences -> preferences[USER_ID] }
    val userType = dataStore.data.map { preferences -> preferences[USER_ID_TYPE] }
    val token = dataStore.data.map { preferences -> preferences[TOKEN] }
    val tokenExpiredAt = dataStore.data.map { preferences -> preferences[TOKEN_EXPIRED_AT] }
    val refreshToken = dataStore.data.map { preferences -> preferences[REFRESH_TOKEN] }
    val refreshTokenExpiredAt = dataStore.data.map { preferences -> preferences[REFRESH_TOKEN_EXPIRED_AT] }


    suspend fun setUserId(id: Long) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }

    suspend fun setUserIdType(id: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID_TYPE] = id
        }
    }

    suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun setTokenExpiredAt(tokenExpiredAt: Long) {
        dataStore.edit { preferences ->
            preferences[TOKEN_EXPIRED_AT] = tokenExpiredAt
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun setRefreshTokenExpiredAt(refreshTokenExpiredAt: Long) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_EXPIRED_AT] = refreshTokenExpiredAt
        }

    }

    suspend fun setUserImageLink(userImageLink: String) {
        dataStore.edit { preferences ->
            preferences[USER_IMAGE_LINK] = userImageLink
        }
    }


    //user----------------------------------------
    val email = dataStore.data.map { preferences -> preferences[EMAIL] }
    val name = dataStore.data.map { preferences -> preferences[NAME] }
    val organisationId = dataStore.data.map { preferences -> preferences[ORG_ID] }
    val phoneNumber = dataStore.data.map { preferences -> preferences[PH_NUMBER] }
    val profileImage = dataStore.data.map { preferences -> preferences[USER_IMAGE_LINK] }
    val dialCode = dataStore.data.map { preferences -> preferences[DIAL_CODE] }
    val designation = dataStore.data.map { preferences -> preferences[DESIGNATION] }


    suspend fun setEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    suspend fun setName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun setOrganisationId(organisationId: String) {
        dataStore.edit { preferences ->
            preferences[ORG_ID] = organisationId
        }
    }

    suspend fun setPhoneNumber(phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[PH_NUMBER] = phoneNumber
        }
    }

    suspend fun setCountryCode(dialCode: String) {
        dataStore.edit { preferences ->
            preferences[DIAL_CODE] = dialCode
        }
    }

    suspend fun setDesignation(designation: String) {
        dataStore.edit { preferences ->
            preferences[DESIGNATION] = designation
        }
    }



    // FCM topic subscriptions

    val subscribedCourseIds: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[subscribedCoursesKey].orEmpty().toSet()
    }

    suspend fun addSubscribedCourse(topic: String) {
        dataStore.edit { preferences ->
            val current = preferences[subscribedCoursesKey].orEmpty()
            preferences[subscribedCoursesKey] = current + topic
        }
    }

    suspend fun removeSubscribedCourse(courseId: String) {
        dataStore.edit { preferences ->
            val current = preferences[subscribedCoursesKey].orEmpty()
            preferences[subscribedCoursesKey] = current - courseId.toString()
        }
    }
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    suspend fun clearSubscribedCourses() {
        dataStore.edit { preferences ->
            preferences.remove(subscribedCoursesKey)
        }
    }

}