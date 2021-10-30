package com.example.missingpets.viewModels

import androidx.lifecycle.ViewModel
import java.util.*

class DetailViewModel(nombreMascota: String, tipoAnimal: String, unTipoAnimal: String, sexoAnimal: String, fechaPerdido : Date)
    : ViewModel(){
    private var nombreMascota: String = nombreMascota
    private var tipoAnimal: String = tipoAnimal
    private var sexoAnimal: String = sexoAnimal
    private var fechaPerdido: Date = fechaPerdido



}