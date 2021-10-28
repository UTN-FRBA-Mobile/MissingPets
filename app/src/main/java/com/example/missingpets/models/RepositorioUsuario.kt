package com.example.missingpets.models

import java.util.*

object RepositorioUsuario {

    var usuarioLogueado: Usuario? = null

    fun registrar(unUsuario: Usuario): ResultadoRegistroUsuario{
        //TODO
        val error: String? = null
        usuarioLogueado = unUsuario.copy()
        return ResultadoRegistroUsuario(unUsuario, error)
    }

    fun loguar(unUsuario: Usuario): ResultadoRegistroUsuario{
        //TODO
        val error: String? = null
        usuarioLogueado = unUsuario.copy()
        return ResultadoRegistroUsuario(unUsuario, error)
    }

    fun estasLogueado(): Boolean {
        return usuarioLogueado != null
    }
}