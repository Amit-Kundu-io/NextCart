/**
 * File: User.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val maidenName: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    val image: String,
    val phone: String? = null,
    val address: Address? = null
)

@Serializable
data class Address(
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null
)