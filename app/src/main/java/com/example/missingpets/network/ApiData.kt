package com.example.missingpets.network

import com.squareup.moshi.Json

data class ApiData (
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
)