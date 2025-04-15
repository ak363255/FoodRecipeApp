package com.example.foodrecipe.ui.di

import com.example.data.BuildConfig
import com.example.data.network.ApiService
import com.example.data.network.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log

val networkmodule = module {

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<OkHttpClient> {
        val logginInterceptor :HttpLoggingInterceptor = get()
        OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .callTimeout(timeout = 200, unit = TimeUnit.SECONDS)
            .build()
    }


    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SPONACULAR_BASE_URL)
            .build()
    }
    single<ApiService> {
        val retrofit : Retrofit = get()
        retrofit.create(ApiService::class.java)
    }
    single {
        NetworkHelper(get())
    }
}