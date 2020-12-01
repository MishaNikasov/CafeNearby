package com.nikasov.cafenearby.data.network.places

import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import javax.inject.Inject

class PlaceApiRepository @Inject constructor(
    private val placeApi: PlaceApi
) {
    fun getCurrentPlace(): Task<FindCurrentPlaceResponse> {
        return placeApi.getCurrentPlace()
    }
    fun getPlaceById(placeId: String): Task<FetchPlaceResponse> {
        return placeApi.getPlaceById(placeId)
    }
}