package com.nikasov.cafenearby.data.local.room.dao

import androidx.room.*
import com.nikasov.cafenearby.data.local.room.entity.CafeEntity

@Dao
interface CafeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCafe(cafeEntity: CafeEntity)
    @Query("SELECT * FROM cafe_table")
    suspend fun getAllCafe(): List<CafeEntity>
    @Delete
    suspend fun deleteCafe(cafeEntity: CafeEntity)
}