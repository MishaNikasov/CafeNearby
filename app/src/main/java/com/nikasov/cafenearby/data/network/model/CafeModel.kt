package com.nikasov.cafenearby.data.network.model

import com.google.android.libraries.places.api.model.PhotoMetadata

data class CafeModel(
    val id: String?,
    val title: String?,
    val rating: Double?,
    val address: String?,
    val photoList: List<PhotoMetadata>? = null,
    var isFavorite: Boolean = false
)