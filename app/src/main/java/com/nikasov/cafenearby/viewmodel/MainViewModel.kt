package com.nikasov.cafenearby.viewmodel

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.nikasov.cafenearby.data.local.DataRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository,
    private val placesClient: PlacesClient
): ViewModel() {

    val userId = MutableLiveData<String>()

    fun saveUserId(value: String) {
        viewModelScope.launch {
            dataRepository.saveUserId(value)
        }
    }

    fun readUserId() {
        viewModelScope.launch {
            userId.postValue(dataRepository.readUserId())
        }
    }

    @SuppressLint("MissingPermission")
    fun getCafeList() {
        val placeFields: List<Place.Field> = listOf(Place.Field.NAME)
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result
                for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                    Timber.d("Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}")
                }
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    Timber.d("Place not found: ${exception.statusCode}")
                }
            }
        }
    }
}