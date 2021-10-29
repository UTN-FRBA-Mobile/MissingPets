package com.example.missingpets.models

data class ResultadoLoginUsuario(
    val exitoso: Usuario? = null,
    val error: String? = null
)
