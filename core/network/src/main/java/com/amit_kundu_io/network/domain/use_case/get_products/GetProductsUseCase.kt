/**
 * File: GetProductsUseCase.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.domain.use_case.get_products

import com.amit_kundu_io.network.data.model.login.AuthResponse
import com.amit_kundu_io.network.data.model.product.Products
import com.amit_kundu_io.network.domain.repo.CommonRepo
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.safeApiCall
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val commonRepo: CommonRepo

) {

    operator fun invoke(
        limit: Int = 20,
        skip: Int = 0,
        search: String = "",
        sortBy: String? = null,
        order: String = "asc",
        category: String? = null,

        ): Flow<NetworkResource<Products>> = safeApiCall(
        apiCall = {
            commonRepo.getProducts(
                limit = limit,
                skip = skip,
                search = search,
                sortBy = sortBy,
                order = order,
                category = category,

            )
        },
    )

}