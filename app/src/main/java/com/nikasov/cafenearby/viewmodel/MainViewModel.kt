package com.nikasov.cafenearby.viewmodel

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.nikasov.cafenearby.data.TypeConverter
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.utils.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
    private val placesClient: PlacesClient
): ViewModel() {

    val cafeList = MutableLiveData(arrayListOf<CafeModel>())
    val uiState = MutableLiveData<UiState>(UiState.Empty)
    val cafeDetails = MutableLiveData<CafeModel>()

    @SuppressLint("MissingPermission")
    fun getCafeList() {
        uiState.postValue(UiState.Loading)
        val placeFields: List<Place.Field> = listOf(
            Place.Field.NAME,
            Place.Field.TYPES,
            Place.Field.ADDRESS,
            Place.Field.ID,
            Place.Field.PHOTO_METADATAS
        )
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uiState.postValue(UiState.Success)
                val response = task.result
                val placeList = response?.placeLikelihoods ?: emptyList()
                convertToCafeModel(placeList)
                for (placeLikelihood: PlaceLikelihood in placeList) {
                    Timber.d("Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}")
                }
            } else {
                val exception = task.exception
                uiState.postValue(UiState.Error(exception?.localizedMessage?: "Error"))
                if (exception is ApiException) {
                    Timber.d("Place not found: ${exception.statusCode}")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCafeDetails(placeId: String) {
        uiState.postValue(UiState.Loading)
        val placeFields: List<Place.Field> = listOf(
            Place.Field.NAME,
            Place.Field.TYPES,
            Place.Field.ADDRESS,
            Place.Field.ID,
            Place.Field.PHOTO_METADATAS
        )
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        val placeResponse = placesClient.fetchPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uiState.postValue(UiState.Success)
                cafeDetails.postValue(TypeConverter.placeToCafe(task.result.place))
            } else {
                val exception = task.exception
                uiState.postValue(UiState.Error(exception?.localizedMessage?: "Error"))
                if (exception is ApiException) {
                    Timber.d("Place not found: ${exception.statusCode}")
                }
            }
        }
    }

    private fun convertToCafeModel(placesList: List<PlaceLikelihood>) {
        viewModelScope.launch {
            val list = arrayListOf<CafeModel>()
            val typesList = listOf(
                Place.Type.CAFE,
                Place.Type.RESTAURANT,
                Place.Type.FOOD,
                Place.Type.BAR
            )
            placesList.asFlow()
                .filter {
                     it.place.types?.forEach { type ->
                        if (typesList.contains(type)) {
                            return@filter true
                        }
                    }
                    false
                }
                .map {
                    list.add(TypeConverter.placeLikelihoodToCafe(it))
                }
                .collect {
                    cafeList.postValue(list)
                }
        }
    }
}