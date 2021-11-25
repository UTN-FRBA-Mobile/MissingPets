package com.example.missingpets.dataRV

import android.util.Log
import com.example.missingpets.models.RepositorioMascota.mascota
import com.example.missingpets.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MissingDatasource {


    /*fun agregarMascota(mascota: Mascota): Boolean {
        var rdo: Boolean = false
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            try {
                // TODO: CUANDO HACE ESTA LLAMADA addLost SE CUELGA, VER POR QUE ?
                var result = ApiClient.backendService.addLost(mascota)
                Log.d("ERROR", "RESULT: "+ result.toString())
                rdo = true
            } catch (e: Exception) {
                Log.d("ERROR", "Error Occurred: ${e.message}")
            }
        }
        return rdo
    }*/

    fun cargarMascotaHard(): Mascota {
        var mascota : Mascota
        mascota = Mascota(0,0,"Mascotita","Perro",
            "Macho", Date().toString(),null,"L",12f,12f,"Se perdio en el parque de la costa")
        return mascota
    }

}