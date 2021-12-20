package com.example.weatherapp

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Response
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var vm: myviewmodel
    lateinit var locale: Locale
    lateinit var sdf: SimpleDateFormat
    var my_icon: String = "01n"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        vm = ViewModelProvider.AndroidViewModelFactory(application).create(myviewmodel::class.java)
        vm.getMyLocation()
        sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        binding.swipe.setOnRefreshListener {
            vm.getkey()
            vm.getweather()
            vm.myname.observe(this, {
                binding.apply {
                    txt.text = it.name
                    sunrise.text =
                        "SUNRISE : " + sdf.format(((it.my_sys.sunrise).toLongOrNull())?.times(1000))
                    sunset.text =
                        "SUNSET : " + sdf.format(((it.my_sys.sunset).toLongOrNull())?.times(1000))
                    condition.text = it.my_weather[0].main
                    locale = Locale("", it.my_sys.country)
                    country.text = locale.displayCountry
                    temp.text = round(it.main_temp.temp.toDouble()).toString() + "°C"
                    feelsLike.text = "feels like " + it.main_temp.feels_like + "°C"
                }
                my_icon = it.my_weather[0].icon

                try {
                    binding.icon.load("https://openweathermap.org/img/wn/$my_icon@2x.png")
                } catch (e: Exception) {

                }
            })
            binding.swipe.isRefreshing = false
        }
    }

    override fun onStart() {
        vm.getkey()
        vm.getweather()
        vm.myname.observe(this, {
            binding.apply {
                txt.text = it.name
                sunrise.text =
                    "SUNRISE : " + sdf.format(((it.my_sys.sunrise).toLongOrNull())?.times(1000))
                sunset.text =
                    "SUNSET : " + sdf.format(((it.my_sys.sunset).toLongOrNull())?.times(1000))
                condition.text = it.my_weather[0].main
                locale = Locale("", it.my_sys.country)
                country.text = locale.displayCountry
                temp.text = round(it.main_temp.temp.toDouble()).toString() + "°C"
                feelsLike.text = "feels like " + it.main_temp.feels_like + "°C"
            }
            my_icon = it.my_weather[0].icon
        })
        try {
            binding.icon.load("https://openweathermap.org/img/wn/$my_icon@2x.png")
        } catch (e: Exception) {

        }
        super.onStart()
    }

}





