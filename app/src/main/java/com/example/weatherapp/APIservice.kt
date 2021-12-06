package com.example.weatherapp

import androidx.activity.viewModels
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL =
    "https://api.openweathermap.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface APIservice {

    @GET("data/2.5/weather?appid=90592a5ce549d86156accbdcfdab7c65")
    suspend fun getnames(
        @Query("lat")lat:String,
        @Query("lon")lon:String
    ): weather
}

object myApi {
    val retrofitService: APIservice by lazy {
        retrofit.create(APIservice::class.java)
    }
}