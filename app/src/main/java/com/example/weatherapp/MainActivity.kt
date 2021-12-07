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
import com.google.android.gms.common.api.Response
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var vm:myviewmodel
    lateinit var locale: Locale
    lateinit var sdf:SimpleDateFormat
    var my_icon:String="01n"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm =ViewModelProvider.AndroidViewModelFactory(application).create(myviewmodel::class.java)
        sdf= SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
        val txt:TextView = findViewById(R.id.txt)
        val swipe:SwipeRefreshLayout=findViewById(R.id.swipe)
        val coun:TextView=findViewById(R.id.country)
        val sunr:TextView=findViewById(R.id.sunrise)
        val suns:TextView=findViewById(R.id.sunset)
        val an:TextView=findViewById(R.id.another)
        val img:ImageView=findViewById(R.id.icon)
        vm.myname.observe(this, {
            txt.text = it.name
            coun.text=it.my_sys.country
            sunr.text=it.my_sys.sunrise
            suns.text=it.my_sys.sunset
            an.text=it.my_weather[0].main
            my_icon=it.my_weather[0].icon
        })
        try {
            img.load("https://openweathermap.org/img/wn/$my_icon@2x.png")
        }
        catch (e:Exception){

        }
        swipe.setOnRefreshListener {
            vm.getkey()
            vm.getMarsPhotos()
            vm.myname.observe(this, {
                txt.text = it.name
                sunr.text="SUNRISE : "+sdf.format(((it.my_sys.sunrise).toLongOrNull())?.times(1000))
                suns.text="SUNSET : "+ sdf.format(((it.my_sys.sunset).toLongOrNull())?.times(1000))
                an.text=it.my_weather[0].main
                my_icon=it.my_weather[0].icon
                locale=Locale("",it.my_sys.country)
                coun.text=locale.displayCountry

                try {
                    img.load("https://openweathermap.org/img/wn/$my_icon@2x.png")
                }
                catch (e:Exception){

                }
            })
            swipe.isRefreshing=false
        }
        }



    }





