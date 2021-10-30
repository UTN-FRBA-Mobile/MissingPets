package com.example.missingpets.models

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.missingpets.R
import java.util.*

object RepositorioUsuario {

    var usuarioLogueado: Usuario? = null

    fun registrar(unUsuario: Usuario): ResultadoRegistroUsuario{
        //TODO, llamar a un service que le pegue al backend con Retrofit
        val error: String? = null

        val respuestaUsuario: Usuario = Usuario()
        usuarioLogueado = unUsuario.copy()
        return ResultadoRegistroUsuario(respuestaUsuario, error)
    }

    fun loguar(unUsuario: Usuario): ResultadoLoginUsuario{
        //TODO, llamar a un service que le pegue al backend con Retrofit
        val error: String? = null

        val respuestaUsuario: Usuario = unUsuario.copy()
        usuarioLogueado = respuestaUsuario.copy()
        return ResultadoLoginUsuario(respuestaUsuario, error)
    }

    fun siNoEstasLogueadoEnviarAlLoguin(fragment: Fragment){
        //cuando lo llames de un fragment, el parametro a pasar va a ser "this"
        if (!this.estasLogueado()){
            val action = R.id.action_registerFragment_to_loginFragment2
            findNavController(fragment).navigate(action)
        }
    }

    fun estasLogueado(): Boolean {
        return usuarioLogueado != null
    }
}