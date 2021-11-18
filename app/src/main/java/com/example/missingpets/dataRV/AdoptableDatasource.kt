package com.example.missingpets.dataRV

import android.util.Log
import com.example.missingpets.network.ApiClient
import com.example.missingpets.network.recyclerPet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class AdoptableDatasource {

    fun loadAdoptablePets(): List<recyclerPet> {
        var lost: List<recyclerPet> = arrayListOf()
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            try {
                val response = ApiClient.backendService.getFound()

                if (response.isSuccessful && response.body() != null) {
                    val content = response.body()
                    lost = content?.toList()!!
                    Log.d("lost", lost.toString())
                    //do something
                } else {
                    Log.d("ERROR", "Error Occurred: ${response.message()}")
                }

            } catch (e: Exception) {
                Log.d("ERROR", "Error Occurred: ${e.message}")
            }
        }
        return lost
    }

}