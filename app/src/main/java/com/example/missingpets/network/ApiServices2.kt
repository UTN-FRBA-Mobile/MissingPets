package com.example.missingpets.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiServices2 {

    @GET("lost")
    fun getMissingPets(): Call<List<MissingPet>>

    companion object {

        var BASE_URL = "https://sea.net.ar/missingpets/api/"

        fun create(): ApiServices2 {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiServices2::class.java)
        }
    }

}