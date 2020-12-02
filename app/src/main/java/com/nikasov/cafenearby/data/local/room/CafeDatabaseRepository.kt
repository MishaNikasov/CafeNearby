package com.nikasov.cafenearby.data.local.room

import androidx.lifecycle.LiveData
import com.nikasov.cafenearby.data.local.room.dao.CafeDao
import com.nikasov.cafenearby.data.local.room.entity.CafeEntity
import javax.inject.Inject

class CafeDatabaseRepository @Inject constructor(
    private val cafeDao: CafeDao
) {
    suspend fun insertCafe(cafeEntity: CafeEntity) { cafeDao.insertCafe(cafeEntity) }
    fun getAllCafe(): LiveData<List<CafeEntity>> { return cafeDao.getAllCafe() }
    suspend fun deleteCafe(cafeEntity: CafeEntity) { cafeDao.deleteCafe(cafeEntity) }
}