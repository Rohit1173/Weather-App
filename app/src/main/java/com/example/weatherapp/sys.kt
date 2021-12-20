package com.example.weatherapp

import com.squareup.moshi.Json

data class sys(
    @Json(name = "country") val country: String,
    @Json(name = "sunrise") val sunrise: String,
    @Json(name = "sunset") val sunset: String
)
