package com.example.missingpets.viewModels

import androidx.lifecycle.ViewModel
import com.example.missingpets.models.RepositorioUsuario

class DetailViewModel(nombreMascota: String, tipoAnimal: String, sexoAnimal: String, fechaPerdido : String): ViewModel(){

    private var nombreMascota: String = nombreMascota
    private var tipoAnimal: String = tipoAnimal
    private var sexoAnimal: String = sexoAnimal
    private var fechaPerdido: String = fechaPerdido
    private var numeroTelefono: String = ""
    private var usernameCreadorPost: String = ""

    fun contactar(){
        //TODO
    }

    fun setUsernameCreadorPost(usernameCreadorPost: String){
        this.usernameCreadorPost = usernameCreadorPost
    }

    fun getUsernameCreadorPost(): String{
        return this.usernameCreadorPost
    }

    fun setNumeroTelefono(numeroTelefono: String){
        this.numeroTelefono = numeroTelefono
    }

    fun getNumeroTelefono(): String{
        return this.numeroTelefono
    }

}