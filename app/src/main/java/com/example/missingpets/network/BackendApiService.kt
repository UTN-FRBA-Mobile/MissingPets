package com.example.missingpets.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET

import retrofit2.http.FormUrlEncoded
import retrofit2.http.Url








private const val BASE_URL = "https://sea.net.ar/missingpets/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//Defino los endpoints
public interface BackendApiService {
    @GET("photos")
    suspend fun getPhotos(): List<ApiData>

    @GET("lost")
    suspend fun getLost(): List<MissingPet>

/*    @FormUrlEncoded
    @DELETE("lost")
    suspend fun deleteLost(@Query("id") absenceId: Int, @Url url: String?): Call<ServerResponse?>?
 */

}

public fun getRetrofit(): Retrofit {
    return retrofit;
}


object BackendApi {
    val retrofitService : BackendApiService by lazy {
        retrofit.create(BackendApiService::class.java) }
}
