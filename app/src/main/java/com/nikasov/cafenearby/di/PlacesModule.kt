package com.nikasov.cafenearby.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class PlacesModule {

    @Singleton
    @Provides
    fun providePlaces(@ApplicationContext context: Context): PlacesClient {
        return Places.createClient(context)
    }

}