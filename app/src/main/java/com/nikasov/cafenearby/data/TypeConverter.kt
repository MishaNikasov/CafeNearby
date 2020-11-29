package com.nikasov.cafenearby.data

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.nikasov.cafenearby.data.network.model.CafeModel

object TypeConverter {
    fun placeLikelihoodToCafe(place: PlaceLikelihood): CafeModel {
        return CafeModel(
            place.place.id,
            place.place.name,
            place.place.rating,
            place.place.address,
            place.place.photoMetadatas
        )
    }
    fun placeToCafe(place: Place): CafeModel {
        return CafeModel(
            place.id,
            place.name,
            place.rating,
            place.address,
            place.photoMetadatas
        )
    }
}