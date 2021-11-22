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

    @Test
    fun postPet() {
        var pet: Mascota = Mascota()

        //TODO asignar ID del usuario loggeado
        pet.idcreator = 0

        //TODO leer coordenadas del mapa
        //pet.latitude = ???
        //pet.longitude = ???

        pet.description = ""

        //TODO hacer el post de la foto y obtener el path
        pet.photopath = "gato.jpg"

        pet.nombreMascota = ""
        pet.tipoAnimal = ""
        pet.sexoAnimal = ""

        //TODO validar formato de fecha
        pet.fechaPerdido = "2021-01-10"

        pet.estado = "perdido"

        var id = ApiServices2.create().publicarMascota(pet)
        Log.d("Unit Test", "id devuelto por el servidor"+ id)
    }

    /*@T est
    fun retrieveUserId() {
        var id = ApiServices2.create().getLostById(0)
        Log.d("Unit Test", "id devuelto por el servidor"+ id)
    }*/
}