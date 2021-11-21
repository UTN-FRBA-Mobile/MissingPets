package com.example.missingpets.network

import com.squareup.moshi.Json

data class ApiData (
    val description: String,
    @Json(name = "img_src") val imgSrcUrl: String
)


data class MissingPet (
    @Json(name = "id") val id: Int,
    @Json(name = "idcreator") val idcreator: Int,
    @Json(name = "idmascota") val idmascota: Int? = null,
  //  @Json(name = "description") val description: String,
  //  @Json(name = "latitude") val latitude: Float,
  //  @Json(name = "longitude") val longitude: Float,
  //  @Json(name = "photopath") val photopath: String,
    @Json(name = "creationdate") val creationdate: String


//    @DrawableRes val imageResourceId: Int
)

data class Mascota (
    @Json(name = "id") val id: Int? = null,
    @Json(name = "idcreator") val idcreator: Int? = null,
    @Json(name = "nombremascota") val nombreMascota: String? = null,
    @Json(name = "tipoanimal") val tipoAnimal: String? = null,
    @Json(name = "sexoanimal") val sexoAnimal: String? = null,
    @Json(name = "fechaperdido") val fechaPerdido: String? = null,
    @Json(name = "photopath") val photopath: String? = null,
    @Json(name = "estado") val estado: String? = null,  // L=Lost  F=Found  A=Adoptable  D=Adoptado
    @Json(name = "latitude") val latitude: Float? = null,
    @Json(name = "longitude") val longitude: Float? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "creationdate") val creationdate: String? = null,
)


data class recyclerPet (
    @Json(name = "id") val id: Int,
    @Json(name = "description") val description: String,
    @Json(name = "photopath") val photopath: String,
)
data class User (
    @Json(name = "id") val id: Int? = null,
    @Json(name = "username") val username: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "password") val password: String? = null,
    @Json(name = "phonenumber") val phonenumber: String? = null,
)
