package com.example.missingpets.utils.storage.preferences

import android.content.Context
import android.content.SharedPreferences


class Prefs (context: Context) {
    val PREFS_NAME = "com.cursokotlin.sharedpreferences"
    val SHARED_LATITUDE = "shared_latitude"
    val SHARED_LONGITUDE = "shared_longitude"
    val SHARED_RECYCLER = "shared_recycler"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var latitude: Float
        get() = prefs.getFloat(SHARED_LATITUDE, 0f)
        set(value) = prefs.edit().putFloat(SHARED_LATITUDE, value).apply()

    var longitude: Float
        get() = prefs.getFloat(SHARED_LONGITUDE, 0f)
        set(value) = prefs.edit().putFloat(SHARED_LONGITUDE, value).apply()

    var cualRecycler: String   // L = lost   F = found   A = Adoptable
        get() = prefs.getString(SHARED_RECYCLER, "")!!
        set(value) = prefs.edit().putString(SHARED_RECYCLER, value).apply()
}
