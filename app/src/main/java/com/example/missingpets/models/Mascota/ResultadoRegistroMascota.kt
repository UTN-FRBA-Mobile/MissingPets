package com.example.missingpets.models.Mascota

import com.example.missingpets.network.Mascota

data class ResultadoRegistroMascota(
    val exitoso: Mascota? = null,
    val error: String? = null
)