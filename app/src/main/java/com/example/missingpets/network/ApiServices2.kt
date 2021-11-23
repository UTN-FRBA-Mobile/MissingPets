package com.example.missingpets.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

interface ApiServices2 {

    @GET("lost")
    fun getMissingPets(): Call<List<recyclerPet>>

    @GET("found")
    fun getFound(): Call<List<recyclerPet>>

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

    @GET("lost/")
    fun getLostById(@Query("id") id: Int): Call<List<Mascota>>

    @FormUrlEncoded
    @POST("lost/")
    fun addLost(
        @Field("idcreator") idcreator: Int?,
        @Field("latitude") latitude: Float?,
        @Field("longitude") longitude: Float?,
        @Field("description") description: String?,
        @Field("photopath") photopath: String?,
        @Field("nombremascota") nombremascota: String?,
        @Field("tipoanimal") tipoanimal: String?,
        @Field("sexoanimal") sexoanimal: String?,
        @Field("fechaperdido") fechaperdido: String?,//TODO cambiar por Date
        @Field("estado") estado: String?
        ):  Call<ResponseBody?>?

    //PUT hace update
    @PUT("lost")
    suspend fun updateLost(@Body missingpet: MissingPet):  Call<ResponseBody?>?

    @FormUrlEncoded
    @DELETE("lost")
    suspend fun deleteLost(@Query("id") id: Int):  Call<ResponseBody?>?


    //Usuarios
    @GET("user")
    suspend fun getAllUsers(): Call<List<User>>

    @GET("user/")
    suspend fun getUserById(@Query("id") id: Int): Call<List<User>>

    @GET("user/")
    suspend fun getUserInfo(@Query("username") username: String, @Query("password") password: String, ): Call<List<User>>

    @POST("user")
    suspend fun addUser(@Body user: User):  Call<ResponseBody?>?

    //PUT hace update
    @PUT("user")
    suspend fun updateUser(@Body user: User):  Call<ResponseBody?>?

    @FormUrlEncoded
    @DELETE("user")
    suspend fun deleteUser(@Query("id") id: Int):  Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("user/")
    suspend fun insertNewUser(@FieldMap params: HashMap<String?,String?>): Response<ResponseBody>

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