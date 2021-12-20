package com.example.weatherapp

import android.app.Activity
import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class myviewmodel(application: Application) : AndroidViewModel(application) {
    var location: FusedLocationProviderClient
    private val context = getApplication<Application>().applicationContext
    private var my_Longitude: String? = null
    private var my_latitude: String? = null
    private lateinit var api: String
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _myweather = MutableLiveData<weather>()
    val myname: LiveData<weather> = _myweather

    init {
        location = LocationServices.getFusedLocationProviderClient(context)
        getkey()
        getMyLocation()
        getweather()
    }

    fun getkey() {
        val ai: ApplicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["keyValue"]
        val key = value.toString()
        api = key
    }

    fun getweather() {

        viewModelScope.launch {
            try {
                _myweather.value =
                    myApi.retrofitService.getnames(my_latitude!!, my_Longitude!!, api)
                _status.value = "SUCCESS"


            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getMyLocation() {
        val task = location.lastLocation
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                my_latitude = it.latitude.toString()
                my_Longitude = it.longitude.toString()
            }

        }
    }
}