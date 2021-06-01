package com.weiwei.beats.weather

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
* retrieve weather data from: (example: Hualien city)
* https://api.openweathermap.org/data/2.5/weather?q=Banqiao&appid=5446cea7c0e250b85d9b6a663da609a9
*
* http://api.openweathermap.org/data/2.5/weather?q=Banqiao&units=metric&lang=zh_tw&appid=5446cea7c0e250b85d9b6a663da609a9
*/

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(WeatherViewModel.API_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface AppService {
    @GET("data/2.5/weather?")
    //fun getAppData(): Call<WeatherData>
    //fun getAppData(@Query("q") location:String, @Query("units") unit: String, @Query("lang") lang: String, @Query("appid") api_key: String): Call<WeatherData>
    suspend fun getAppData(@Query("q") location:String, @Query("units") unit: String, @Query("lang") lang: String, @Query("appid") api_key: String): WeatherData
}

//global singleton object
object GetService {
    val retrofitService : AppService by lazy {
        retrofit.create(AppService::class.java) }
}