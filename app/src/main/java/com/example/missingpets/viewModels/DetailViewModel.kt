package com.example.missingpets.viewModels

import androidx.lifecycle.ViewModel
import com.example.missingpets.models.RepositorioUsuario

class DetailViewModel(nombreMascota: String, tipoAnimal: String, sexoAnimal: String, fechaPerdido : String): ViewModel(){

    private var nombreMascota: String = nombreMascota
    private var tipoAnimal: String = tipoAnimal
    private var sexoAnimal: String = sexoAnimal
    private var fechaPerdido: String = fechaPerdido

    fun contactar(){
        //TODO
    }

    fun getNumeroTelefono(): String{
        return "5491169013434"
    }

}