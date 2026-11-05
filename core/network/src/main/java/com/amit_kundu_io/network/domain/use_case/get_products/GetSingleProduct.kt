/**
 * File: GetSingleProduct.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.domain.use_case.get_products

import com.amit_kundu_io.network.data.model.product.Product
import com.amit_kundu_io.network.data.model.product.Products
import com.amit_kundu_io.network.domain.repo.CommonRepo
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.safeApiCall
import kotlinx.coroutines.flow.Flow

class GetSingleProduct(
    private val commonRepo: CommonRepo

) {

    operator fun invoke(
        productId: Long
        ): Flow<NetworkResource<Product>> = safeApiCall(
        apiCall = {
            commonRepo.getProductById(productId = productId )
        },
    )

}