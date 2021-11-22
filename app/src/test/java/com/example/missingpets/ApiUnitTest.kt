package com.example.missingpets

import android.util.Log
import com.example.missingpets.dataRV.UserDatasource
import com.example.missingpets.network.ApiServices2
import org.junit.Test

import org.junit.Assert.*
import kotlin.reflect.typeOf

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTest {
    @Test
    fun retrievePet() {
        var id = ApiServices2.create().getLostById(id = 30)
        Log.d("Unit Test", "id devuelto por el servidor"+ id)
       // assert(typeOf(id).equals(List))
    }

    /*@T est
    fun retrieveUserId() {
        var id = ApiServices2.create().getLostById(0)
        Log.d("Unit Test", "id devuelto por el servidor"+ id)
    }*/
}