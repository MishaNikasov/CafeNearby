package com.nikasov.cafenearby.data

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.nikasov.cafenearby.data.local.room.entity.CafeEntity
import com.nikasov.cafenearby.data.network.model.CafeModel
import java.util.*

fun PlaceLikelihood.toCafe(): CafeModel {
    return CafeModel(
        this.place.id,
        this.place.name,
        this.place.rating,
        this.place.address,
        this.place.photoMetadatas
    )
}

fun Place.toCafe(): CafeModel {
    return CafeModel(
        this.id,
        this.name,
        this.rating,
        this.address,
        this.photoMetadatas
    )
}

fun CafeModel.toCafeEntity(): CafeEntity {
    return CafeEntity(
        this.id?: "${UUID.randomUUID()}",
        this.title,
        this.rating,
        this.address,
        this.isFavorite
    )
}

fun CafeEntity.toCafeModel(): CafeModel {
    return CafeModel(
        this.id,
        this.title,
        this.rating,
        this.address,
        null,
        this.isFavorite
    )
}
