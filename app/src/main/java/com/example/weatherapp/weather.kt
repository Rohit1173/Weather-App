package com.example.weatherapp

import com.squareup.moshi.Json

data class weather(
      val id:Int,
      @Json(name="name") val name:String,
      @Json(name="sys") val my_sys:sys,
      @Json(name="weather") val my_weather:List<weather_class>,
      @Json(name="main")val main_temp:main_temp

)
