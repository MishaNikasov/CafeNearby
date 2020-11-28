package com.nikasov.cafenearby.data.local

import com.nikasov.cafenearby.data.local.Settings.Companion.USER_ID
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val settings: Settings
) {
    suspend fun saveUserId(value: String) {
        settings.saveString(USER_ID, value)
    }
    suspend fun readUserId(): String? {
        return settings.readString(USER_ID)
    }
}