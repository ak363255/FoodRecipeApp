package com.example.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

object Convertor {

    val gson by lazy {
        Gson()
    }


   inline fun <reified T>convertDataToJson(data:T): String{
      return gson.toJson(data)
   }
    inline fun<reified  T>convertJsonToData(json: String):T?{
        try {
            val type = object : TypeToken<T>() {}.type

            val data : T? = gson.fromJson(json,type)
            return data
        }catch (e: Exception){
            return null
        }
    }
}