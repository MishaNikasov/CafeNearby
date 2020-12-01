package com.nikasov.cafenearby.di

import android.content.Context
import androidx.room.Room
import com.nikasov.cafenearby.data.local.room.CafeDatabase
import com.nikasov.cafenearby.data.local.room.dao.CafeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    private const val CAFE_DATABASE = "cafe_database"

    @Provides
    @Singleton
    fun provideCafeDatabase(@ApplicationContext context: Context): CafeDatabase {
        return Room.databaseBuilder(
            context,
            CafeDatabase::class.java,
            CAFE_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideCafeDAO(cafeDatabase: CafeDatabase): CafeDao {
        return cafeDatabase.getCafeDao()
    }
}