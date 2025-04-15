package com.example.data.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
     suspend fun makeGetRequest(@Url url:String): retrofit2.Response<ResponseBody>

}