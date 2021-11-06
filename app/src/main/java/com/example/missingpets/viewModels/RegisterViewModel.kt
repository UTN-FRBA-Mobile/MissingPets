package com.example.missingpets.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.models.ResultadoRegistroUsuario
import com.example.missingpets.models.Usuario

class RegisterViewModel : ViewModel(){

    //var registroFinalizadoExitosamente = MutableLiveData<Boolean>()
    var resultadoRegistro = MutableLiveData<ResultadoRegistroUsuario>()
    val repositorioUsuario = RepositorioUsuario

    init{
    }

    fun registrar(username: String?, email: String?, contrasenia: String?, phoneNumber: String?){
        if(algunCampoEstaVacio(username, email, contrasenia, phoneNumber)){
            resultadoRegistro.value = ResultadoRegistroUsuario(null, "Debe completar todos los campos")
        }
        else{
            val usuario = Usuario(username, phoneNumber, email, contrasenia)
            resultadoRegistro.value = repositorioUsuario.registrar(usuario)
        }
    }

    fun algunCampoEstaVacio(username: String?, email: String?, contrasenia: String?, phoneNumber: String?): Boolean{
        return username.isNullOrBlank() || email.isNullOrBlank() || contrasenia.isNullOrBlank() || phoneNumber.isNullOrBlank()
    }

}