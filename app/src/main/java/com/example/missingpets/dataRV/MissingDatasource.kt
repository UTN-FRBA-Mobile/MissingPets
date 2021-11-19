package com.example.missingpets.dataRV

import android.util.Log
import com.example.missingpets.network.ApiClient
import com.example.missingpets.network.recyclerPet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MissingDatasource {
    fun loadMissingPetsLocal(): List<recyclerPet> {
        val ruta: String = "http://c.files.bbci.co.uk/48DD/production/_107435681_perro1.jpg"
        return listOf(
            recyclerPet(1,"Hola, me perdi ayer a las 18:15 cerca de la plaza.", ruta),
            recyclerPet(2,"Se busca, recompensa $$$", ruta),
            recyclerPet(3,"Ayudaaaaa", ruta),
            recyclerPet(4,"Se perdioooooo", ruta),
            recyclerPet(5,"Si alguien lo ve llame al WP: 000000000", ruta),
            recyclerPet(6,"Se busca, recompensa $$$", ruta),
            recyclerPet(7,"Hola, me perdi ayer a las 18:00 cerca de la plaza.", ruta),
            recyclerPet(8,"Hola, me perdi ayer a las 18:00 cerca de la plaza.", ruta),
            recyclerPet(9,"Si alguien lo ve llame al WP: 000000000", ruta),
            recyclerPet(10,"Si alguien lo ve llame al WP: 000000000", ruta)
        )
    }
}