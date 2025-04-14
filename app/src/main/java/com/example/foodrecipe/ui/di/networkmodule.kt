package com.example.foodrecipe.ui.di

import com.example.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.time.Duration
import java.util.concurrent.TimeUnit

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
            .baseUrl(BuildConfig.SPONACULAR_BASE_URL)

    }
}