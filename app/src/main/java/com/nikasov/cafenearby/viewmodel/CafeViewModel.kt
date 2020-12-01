package com.nikasov.cafenearby.viewmodel

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.nikasov.cafenearby.data.local.room.CafeDatabaseRepository
import com.nikasov.cafenearby.data.network.model.CafeModel
import com.nikasov.cafenearby.data.network.places.PlaceApiRepository
import com.nikasov.cafenearby.data.toCafe
import com.nikasov.cafenearby.data.toCafeEntity
import com.nikasov.cafenearby.data.toCafeModel
import com.nikasov.cafenearby.utils.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class CafeViewModel @ViewModelInject constructor(
    private val placeApiRepository: PlaceApiRepository,
    private val cafeDatabaseRepository: CafeDatabaseRepository
): ViewModel() {

    val uiState = MutableLiveData<UiState>(UiState.Empty)

    val cafeDetails = MutableLiveData<CafeModel>()
    val cafeList = MutableLiveData(arrayListOf<CafeModel>())

    @SuppressLint("MissingPermission")
    fun getCafeList() {
        uiState.postValue(UiState.Loading)

        placeApiRepository.getCurrentPlace().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uiState.postValue(UiState.Success)
                val response = task.result
                val placeList = response?.placeLikelihoods ?: emptyList()
                convertPlacesToCafeList(placeList)
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

        placeApiRepository.getPlaceById(placeId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uiState.postValue(UiState.Success)
                cafeDetails.postValue((task.result.place).toCafe())
            } else {
                val exception = task.exception
                uiState.postValue(UiState.Error(exception?.localizedMessage?: "Error"))
                if (exception is ApiException) {
                    Timber.d("Place not found: ${exception.statusCode}")
                }
            }
        }
    }

    fun addCafeToFavorite(cafeModel: CafeModel) {
        viewModelScope.launch {
            cafeDatabaseRepository.insertCafe(cafeEntity = cafeModel.toCafeEntity())
        }
    }

    fun deleteFromFavorite(cafeModel: CafeModel) {
        viewModelScope.launch {
            cafeDatabaseRepository.deleteCafe(cafeEntity = cafeModel.toCafeEntity())
        }
    }

    fun getFavoriteCafe() {
        viewModelScope.launch {
            val list = arrayListOf<CafeModel>()
            cafeDatabaseRepository.getAllCafe().asFlow()
                .map {
                    it.toCafeModel()
                }
                .collect {
                    list.add(it)
                }
            cafeList.postValue(list)
        }
    }

    private fun convertPlacesToCafeList(placesList: List<PlaceLikelihood>) {
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
                    true
                }
                .map {
                    it.toCafe()
                }
                .collect {
                    list.add(it)
                }

            cafeList.postValue(list)
        }
    }
}