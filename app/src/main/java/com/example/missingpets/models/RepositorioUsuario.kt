package com.example.missingpets.models

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

    fun estasLogueado(): Boolean {
        return usuarioLogueado != null
    }
}