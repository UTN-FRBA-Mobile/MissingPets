package com.example.missingpets.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET

import retrofit2.http.FormUrlEncoded
import retrofit2.http.Url



//Defino los endpoints
interface BackendApiService {
    @GET("photos")
    suspend fun getPhotos(): Call<List<ApiData>>

    @GET("lost")
    suspend fun getLost(): Response<List<recyclerPet>>

    @GET("found")
    suspend fun getFound(): Response<List<recyclerPet>>
}

    object ApiClient {

        private const val BASE_URL = "https://sea.net.ar/missingpets/api/"


        private val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        private val retrofit : Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
        }

        val backendService: BackendApiService by lazy {
            retrofit.create(BackendApiService::class.java)
        }

}


