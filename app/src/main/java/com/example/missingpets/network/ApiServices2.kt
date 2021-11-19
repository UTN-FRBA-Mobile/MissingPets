package com.example.missingpets.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiServices2 {

    @GET("lost")
    fun getMissingPets(): Call<List<MissingPet>>

    companion object {

        var BASE_URL = "https://sea.net.ar/missingpets/api/"

        fun create(): ApiServices2 {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiServices2::class.java)
        }
    }

}