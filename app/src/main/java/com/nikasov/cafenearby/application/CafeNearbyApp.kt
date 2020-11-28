package com.nikasov.cafenearby.application

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.nikasov.cafenearby.R
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CafeNearbyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Places.initialize(applicationContext, getString(R.string.maps_api_key))
    }
}