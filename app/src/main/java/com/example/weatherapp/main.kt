package com.example.weatherapp

import com.squareup.moshi.Json

data class main_temp (
    @Json(name="temp") val temp:String,
    @Json(name = "feels_like") val feels_like:String
    )