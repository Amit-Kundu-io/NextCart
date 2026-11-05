/**
 * File: ApiImpl.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.data.apis

import com.amit_kundu_io.network.data.model.categorie.CategoriesItem
import com.amit_kundu_io.network.data.model.login.AuthResponse
import com.amit_kundu_io.network.data.model.login.LoginRequest
import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.network.data.model.product.Products
import com.amit_kundu_io.network.data.model.user.User
import com.amit_kundu_io.network.domain.repo.CommonRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiImpl(
    private val client: HttpClient
) : CommonRepo {

    override suspend fun getCategories(): List<CategoriesItem> {
        return client.get("/products/categories").body<List<CategoriesItem>>()
    }

    override suspend fun getProducts(
        limit: Int,
        skip: Int,
        search: String,
        sortBy: String?,
        order: String,
        category: String?
    ): Products {
        val query = search.trim()

        val endpoint = when {
            !category.isNullOrBlank() -> "/products/category/${category.trim()}"
            query.isNotEmpty() -> "/products/search"
            else -> "/products"
        }

        return client.get(endpoint) {
            parameter("limit", limit)
            parameter("skip", skip)

            if (category.isNullOrBlank() && query.isNotEmpty()) {
                parameter("q", query)
            }

            sortBy?.let {
                parameter("sortBy", it)
                parameter("order", order)
            }
        }.body()
    }



    override suspend fun getProductById(
        productId: Long
    ): Product {
        return client.get(
            "/products/$productId"
        ).body()
    }

    override suspend fun getFeaturedProducts(
        limit: Int,
        skip: Int
    ): Products {
        return client.get("/products") {
            parameter("sortBy", "rating")
            parameter("order", "desc")
            parameter("limit", limit)
            parameter("skip", skip)
        }.body()
    }

    override suspend fun login(
        request: LoginRequest
    ): AuthResponse {
        return client.post("/auth/login") {
            setBody(request)
        }.body()
    }

    override suspend fun getMe(
        accessToken: String
    ): User {
        return client.get("/auth/me") {
            bearerAuth(accessToken)
        }.body()
    }
}