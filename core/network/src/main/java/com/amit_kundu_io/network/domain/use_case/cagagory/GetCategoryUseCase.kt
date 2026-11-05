/**
 * File: GetGataGorieUseCase.kt
 * Created by Amit Kundu
 * Created on 05-Nov-26
 */

package com.amit_kundu_io.network.domain.use_case.cagagory

import com.amit_kundu_io.network.data.model.categorie.CategoriesItem
import com.amit_kundu_io.network.data.model.product.Products
import com.amit_kundu_io.network.domain.repo.CommonRepo
import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.safeApiCall
import kotlinx.coroutines.flow.Flow

class GetCategoryUseCase (
    private val commonRepo: CommonRepo

) {
    operator fun invoke(): Flow<NetworkResource<List<CategoriesItem>>> = safeApiCall(
        apiCall = {
            commonRepo.getCategories()
        },
    )

}