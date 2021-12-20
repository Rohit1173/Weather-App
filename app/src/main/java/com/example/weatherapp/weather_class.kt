package com.example.weatherapp

import com.squareup.moshi.Json

data class weather_class(
    @Json(name = "main") val main: String,
    @Json(name = "description") val desc: String,
    @Json(name = "icon") val icon: String
)
