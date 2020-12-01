package com.nikasov.cafenearby.data.network.places

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import javax.inject.Inject

class PlaceApi @Inject constructor (
    private val placesClient: PlacesClient
) {

    private val placeFields: List<Place.Field> = listOf(
        Place.Field.NAME,
        Place.Field.TYPES,
        Place.Field.ADDRESS,
        Place.Field.ID,
        Place.Field.PHOTO_METADATAS
    )

    @SuppressLint("MissingPermission")
    fun getCurrentPlace(): Task<FindCurrentPlaceResponse> {
        val request = FindCurrentPlaceRequest.newInstance(placeFields)
        return placesClient.findCurrentPlace(request)
    }

    @SuppressLint("MissingPermission")
    fun getPlaceById(placeId: String): Task<FetchPlaceResponse> {
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)
        return placesClient.fetchPlace(request)
    }
}