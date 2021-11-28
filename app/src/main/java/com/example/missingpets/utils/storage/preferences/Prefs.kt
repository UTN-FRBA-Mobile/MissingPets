package com.example.missingpets.utils.storage.preferences

import android.content.Context
import android.content.SharedPreferences


class Prefs (context: Context) {
    val PREFS_NAME = "com.cursokotlin.sharedpreferences"
    val SHARED_LATITUDE = "shared_latitude"
    val SHARED_LONGITUDE = "shared_longitude"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var latitude: Float
        get() = prefs.getFloat(SHARED_LATITUDE, 0f)
        set(value) = prefs.edit().putFloat(SHARED_LATITUDE, value).apply()

    var longitude: Float
        get() = prefs.getFloat(SHARED_LONGITUDE, 0f)
        set(value) = prefs.edit().putFloat(SHARED_LONGITUDE, value).apply()

    fun inicializar(){
        prefs.edit().putFloat(SHARED_LATITUDE, 0f).apply()
        prefs.edit().putFloat(SHARED_LONGITUDE, 0f).apply()
    }
}
