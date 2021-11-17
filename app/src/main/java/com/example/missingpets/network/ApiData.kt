package com.example.missingpets.network

import com.squareup.moshi.Json

data class ApiData (
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
)


data class MissingPet (
    @Json(name = "id") val id: Int,
    @Json(name = "idcreator") val idcreator: Int,
    @Json(name = "latitude") val latitude: Float,
    @Json(name = "longitude") val longitude: Float,
    @Json(name = "photopath") val photopath: String,
    @Json(name = "creationdate") val creationdate: String,

//    @DrawableRes val imageResourceId: Int
)

data class User (
    @Json(name = "id") val id: Int,
    @Json(name = "username") val username: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "phonenumber") val phonenumber: String,
)
