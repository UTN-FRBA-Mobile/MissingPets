package com.example.missingpets.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.missingpets.models.Usuario

class RegisterViewModel : ViewModel(){

    //var registroFinalizadoExitosamente = MutableLiveData<Boolean>()
    //var registroFinalizado = MutableLiveData<Boolean>()

    init{
        //registroFinalizadoExitosamente.value = false
    }

    fun registrar(username: String?, email: String?, contrasenia: String?){
        val usuario = Usuario(username, email, contrasenia)
        //TODO
    }

    fun camposEstanVacios(username: String?, email: String?, contrasenia: String?): Boolean{
        return username.isNullOrEmpty() || email.isNullOrEmpty() || contrasenia.isNullOrEmpty()
    }

}