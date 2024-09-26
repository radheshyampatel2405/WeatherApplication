package com.example.weatherapplication.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// we define the api
interface WeatherApi
{
    @GET("/v1/current.json")
    suspend fun getWeather(
            @Query("key") apiKey: String,
            @Query("q") city: String
                          ): Response<weatherModel>
}