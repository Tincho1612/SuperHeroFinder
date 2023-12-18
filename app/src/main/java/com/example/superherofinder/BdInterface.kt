package com.example.superherofinder

import com.example.superherofinder.Favoritos.FavoritosResponse
import com.example.superherofinder.Login.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BdInterface {
    @POST(BuildConfig.LINK_DB_LOG)
    suspend fun signIn(@Body user:Userdto): Response<LoginResponse>

    @POST(BuildConfig.LINK_DB_REG)
    suspend fun signUP(@Body user:Registerdto): Response<String>

    @GET("/api/user/favoritos")
    suspend fun getFavs(@Header("access-token") token:String):Response<FavoritosResponse>

}

data class Userdto (
    val email:String,
    val password:String
)

data class Registerdto(
    val nombre:String,
    val apellido:String,
    val email:String,
    val password: String
)

