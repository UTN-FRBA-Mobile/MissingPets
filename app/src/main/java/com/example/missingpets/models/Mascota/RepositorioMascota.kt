package com.example.missingpets.models

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.missingpets.R
import com.example.missingpets.dataRV.MissingDatasource
import com.example.missingpets.dataRV.UserDatasource
import com.example.missingpets.models.Mascota.ResultadoRegistroMascota
import com.example.missingpets.network.Mascota
import java.util.*

object RepositorioMascota {

    var mascota: Mascota? = null

    fun registrar(unMascota: Mascota): ResultadoRegistroMascota {
        //TODO, llamar a un service que le pegue al backend con Retrofit
        val error: String? = null
        // val mascota: Mascota? = MissingDatasource().agregarMascota(unMascota)
        val respuestaMascota: Mascota= Mascota()
        mascota = unMascota.copy()
        return ResultadoRegistroMascota(respuestaMascota, error)
    }

    /**
     * URLs para pruebas:
     * http://sea.net.ar/missingpets/api/lost/
     * http://sea.net.ar/missingpets/api/lost/?id=10
     */



}