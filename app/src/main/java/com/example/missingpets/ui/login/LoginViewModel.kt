package com.example.missingpets.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.models.ResultadoLoginUsuario
import com.example.missingpets.models.Usuario

class LoginViewModel() : ViewModel() {

    var resultadoLogin = MutableLiveData<ResultadoLoginUsuario>()
    val repositorioUsuario = RepositorioUsuario

    init{
    }

    fun loguear(usernameOrEmail: String?, contrasenia: String?){
        if(algunCampoEstaVacio(usernameOrEmail, contrasenia)){
            resultadoLogin.value = ResultadoLoginUsuario(null, "Debe completar todos los campos")
        }
        else{
            var usuario: Usuario = Usuario()
            if (esUnEmail(usernameOrEmail.orEmpty())) {
                usuario = Usuario(email = usernameOrEmail, contrasenia = contrasenia)
            }
            else{
                usuario = Usuario(username = usernameOrEmail, contrasenia = contrasenia)
            }
            resultadoLogin.value = repositorioUsuario.loguar(usuario)
        }
    }

    fun algunCampoEstaVacio(usernameOrEmail: String?, contrasenia: String?): Boolean{
        return usernameOrEmail.isNullOrBlank()  || contrasenia.isNullOrBlank()
    }

    fun esUnEmail(unString: String): Boolean{
        return unString.contains("@")
    }

}