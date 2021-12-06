package com.example.weatherapp

import com.squareup.moshi.Json

data class main (
    @Json(name="temp") val temp:String,
    @Json(name = "pressure") val pressure:String,
    @Json(name="humidity") val humidity:String

        )