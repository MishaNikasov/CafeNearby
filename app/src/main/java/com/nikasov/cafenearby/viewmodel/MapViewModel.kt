package com.nikasov.cafenearby.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel @ViewModelInject constructor(
): ViewModel() {

    val mapIsLoading = MutableLiveData<Boolean>()

    val coordinates = MutableLiveData<LatLng>()
    val map = MutableLiveData<GoogleMap>()

    fun setupMap() {
        map.value?.apply {
            coordinates.value?.let {
                addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("You are here")
                )

                val cameraPosition = CameraPosition.Builder()
                    .target(it)
                    .zoom(15f)
                    .build()

                moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                mapIsLoading.postValue(false)
            }
        }
    }
}