package com.nikasov.cafenearby.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikasov.cafenearby.data.local.room.dao.CafeDao
import com.nikasov.cafenearby.data.local.room.entity.CafeEntity

@Database(
    entities = [CafeEntity::class],
    version = 1
)
abstract class CafeDatabase: RoomDatabase() {
    abstract fun getCafeDao(): CafeDao
}