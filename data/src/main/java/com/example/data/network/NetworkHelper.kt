package com.example.data.network

import android.util.Log
import com.example.data.NetworkResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkHelper(
    val apiService: ApiService
) {
    val gson by lazy {
        Gson()
    }

    suspend inline fun <reified D> makeGetRequest(url: String): NetworkResponse<D> {
        try {
            return withContext(Dispatchers.IO) {
                val response = apiService.makeGetRequest(url)
                convertResponse<D>(response)
            }
        } catch (e: Exception) {
            Log.d("RECIPE", "e ${e.message}")

            return NetworkResponse.Error("API Error ${e.message.toString()}")
        }

    }

    suspend inline fun <reified D> convertResponse(response: Response<ResponseBody>): NetworkResponse<D> {
        return if (response.isSuccessful) {
            Log.d("RECIPE", "success")
            val body = response.body()?.string()
            try {
                val data: D = gson.fromJson(body,D::class.java)
                if (data != null) {
                    Log.d("RECIPE", "converted")
                    NetworkResponse.Success(data)
                } else {
                    Log.d("RECIPE", "gson failure")

                    NetworkResponse.Error("Gson Serialization Error")
                }
            }catch (e: Exception){
                e.printStackTrace()
                Log.d("RECIPE", "exception ${e.message}")
                NetworkResponse.Error("Gson Serialization Error")
            }

        } else {
            Log.d("RECIPE", "failure")

            NetworkResponse.Error(response.errorBody()?.string() ?: "")
        }
    }
}