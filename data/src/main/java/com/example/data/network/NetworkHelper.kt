package com.example.data.network

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

  suspend inline fun<reified D> makeGetRequest(url:String): NetworkResponse<D> {
      try {
         return  withContext(Dispatchers.IO){
                val response = apiService.makeGetRequest(url)
                 convertResponse(response)
          }
      }   catch (e:Exception){
          return NetworkResponse.Error("API Error")
      }

    }

    suspend inline fun<reified D> convertResponse(response:Response<ResponseBody>):NetworkResponse<D>{
          return  if(response.isSuccessful){
                val body = response.body()?.string()
              val data: D = gson.fromJson(body, object : TypeToken<D>() {}.type)
                if(data != null){
                    NetworkResponse.Success(data)
                }else{
                    NetworkResponse.Error("Gson Serialization Error")
                }
          }else{
              NetworkResponse.Error(response.errorBody()?.string()?:"")
          }
    }
}