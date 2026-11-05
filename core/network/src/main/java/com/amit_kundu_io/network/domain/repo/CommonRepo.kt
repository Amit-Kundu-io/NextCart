/**
 * File: CommonRepo.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.domain.repo

import com.amit_kundu_io.network.data.model.categorie.CategoriesItem
import com.amit_kundu_io.network.data.model.login.AuthResponse
import com.amit_kundu_io.network.data.model.login.LoginRequest
import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.network.data.model.product.Products
import com.amit_kundu_io.network.data.model.user.User

interface CommonRepo {

    suspend fun getCategories(): List<CategoriesItem>

    suspend fun getProducts(
        limit: Int,
        skip: Int,
        search: String = ""
    ): Products

    suspend fun getProductById(productId: Int): Product

    suspend fun getFeaturedProducts(
        limit: Int,
        skip: Int
    ): Products

    suspend fun login(
        request: LoginRequest
    ): AuthResponse

    suspend fun getMe(
        accessToken: String
    ): User


}