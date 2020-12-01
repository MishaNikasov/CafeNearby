package com.nikasov.cafenearby.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cafe_table")
data class CafeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String?,
    val title: String?,
    val rating: Double?,
    val address: String?
)