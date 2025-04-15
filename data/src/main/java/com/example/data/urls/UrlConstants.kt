package com.example.data.urls

import android.os.Build
import com.example.data.BuildConfig

object UrlConstants {
    const val BASE_URL = BuildConfig.SPONACULAR_BASE_URL
    const val API_KEY = BuildConfig.SPONACULAR_API_KEY
    val RANDOM_RECIPE_API = BASE_URL + "/recipes/random?apiKey=${API_KEY}"
}