package com.example.missingpets.models

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.missingpets.R
import com.example.missingpets.dataRV.MissingDatasource
import com.example.missingpets.dataRV.UserDatasource
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
        val id = UserDatasource().getUserId("username", "pass");
        if(id == -1) {
            Log.d("SECURITY", "Usuario no registrado!")
        } else {
            Log.d("SECURITY", "Usuario Ok")
            val user = UserDatasource().getUserById(id)
            Log.d("SECURITY", user.toString());
        }


        val error: String? = null

        val respuestaUsuario: Usuario = unUsuario.copy()
        usuarioLogueado = respuestaUsuario.copy()
        return ResultadoLoginUsuario(respuestaUsuario, error)
    }

    fun estasLogueado(): Boolean {
        return usuarioLogueado != null
    }

    fun noEstasLogueado(): Boolean{
        return !this.estasLogueado()
    }
}