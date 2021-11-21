package com.example.missingpets.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import okhttp3.RequestBody
import org.json.JSONArray

//Defino los endpoints
interface BackendApiService {
    @GET("photos")
    suspend fun getPhotos(): Call<List<ApiData>>

    @GET("lost")
    suspend fun getLost(): Response<List<recyclerPet>>

    @GET("found")
    suspend fun getFound(): Response<List<recyclerPet>>
  
      //Fotos
    @Multipart
    @POST("upload")
    open fun addPhoto(
        @Part("description") description: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Call<ResponseBody?>?

    //Mascotas
    @GET("lost")
    suspend fun getAllLost(): List<MissingPet>

    @GET("posts/{id}")
    fun getPostsById(@Path("id") id: Int): okhttp3.Response

    @GET("lost/{id}")
    fun getLostById(@Path("id") id: Int): Mascota

    @POST("lost")
    suspend fun addLost(@Body missingpet: MissingPet):  Call<ResponseBody?>?

    //PUT hace update
    @PUT("lost")
    suspend fun updateLost(@Body missingpet: MissingPet):  Call<ResponseBody?>?

    @FormUrlEncoded
    @DELETE("lost")
    suspend fun deleteLost(@Query("id") id: Int):  Call<ResponseBody?>?


    //Usuarios
    @GET("user")
    suspend fun getAllUsers(): List<User>

    @GET("user")
    suspend fun getUserById(@Query("id") id: Int): User

    @GET("user")
    suspend fun getUserId(@Query("username") username: String,@Query("password") password: String, ): Int

    @POST("user")
    suspend fun addUser(@Body user: User):  Call<ResponseBody?>?

    //PUT hace update
    @PUT("user")
    suspend fun updateUser(@Body user: User):  Call<ResponseBody?>?

    @FormUrlEncoded
    @DELETE("user")
    suspend fun deleteUser(@Query("id") id: Int):  Call<ResponseBody?>?
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



