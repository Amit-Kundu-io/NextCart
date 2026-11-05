package com.amit_kundu_io.network.di

import com.amit_kundu_io.network.client.KtorClientFactory

import io.ktor.client.HttpClient
import org.koin.dsl.module

val getCoreNetworkModule = module {
    single<HttpClient> {
        KtorClientFactory().create()
    }

}