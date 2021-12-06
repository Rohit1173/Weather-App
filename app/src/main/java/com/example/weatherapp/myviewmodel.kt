package com.example.weatherapp

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlin.math.nextTowards

class myviewmodel(application: Application) : AndroidViewModel(application) {
    var location: FusedLocationProviderClient
    private val context = getApplication<Application>().applicationContext
   var my_Longitude:String = ""
   var my_latitude:String = ""
     private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

     private val _myname = MutableLiveData<weather>()
     val myname: LiveData<weather> = _myname

    init {
        location = LocationServices.getFusedLocationProviderClient(context)
        getMarsPhotos()
    }
    fun getMarsPhotos() {
        getMyLocation()
        viewModelScope.launch {
            try {
                _myname.value = myApi.retrofitService.getnames(my_latitude, my_Longitude)
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
         ){
             ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
             return
         }
         task.addOnSuccessListener {
             if(it!=null){
                // Toast.makeText(context,"${it.latitude},${it.longitude}", Toast.LENGTH_LONG).show()
                 my_latitude=it.latitude.toString()
                 my_Longitude=it.longitude.toString()
             }

         }
     }
}