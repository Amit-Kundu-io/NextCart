package com.amit_kundu_io.network.di

import com.amit_kundu_io.network.RefreshToken.RefreshApi
import com.amit_kundu_io.network.RefreshToken.RefreshApiImpl
import com.amit_kundu_io.network.RefreshToken.RefreshTokenManager
import com.amit_kundu_io.network.client.KtorClientFactory
import com.amit_kundu_io.network.data.apis.ApiImpl
import com.amit_kundu_io.network.domain.repo.CommonRepo
import com.amit_kundu_io.network.domain.use_case.cagagory.GetCategoryUseCase
import com.amit_kundu_io.network.domain.use_case.get_products.GetProductsUseCase
import com.amit_kundu_io.network.domain.use_case.get_products.GetSingleProduct
import com.amit_kundu_io.network.domain.use_case.login.LoginUseCase
import com.amit_kundu_io.network.domain.use_case.profile.ProfileUseCase

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val getCoreNetworkModule = module {

    single<HttpClient> { KtorClientFactory().create() }

    singleOf(::ApiImpl).bind<CommonRepo>()

    singleOf(::RefreshTokenManager)

    single<RefreshApi> { RefreshApiImpl(get()) }

    single<HttpClient> { KtorClientFactory().create() }


    factoryOf(::LoginUseCase)
    factoryOf(::GetProductsUseCase)
    factoryOf(::GetCategoryUseCase)
    factoryOf(::ProfileUseCase)
    factoryOf(::GetSingleProduct)


}