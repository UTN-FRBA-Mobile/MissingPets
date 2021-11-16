package com.example.missingpets.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "http://sea.net.ar/missingpets/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface BackendApiService {
    @GET("photos")
    suspend fun getPhotos(): List<ApiData>
}

object MarsApi {
    val retrofitService: BackendApiService by lazy { retrofit.create(BackendApiService::class.java) }
}
