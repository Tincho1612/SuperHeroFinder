package com.example.superherofinder

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BdInterface {
    @POST(BuildConfig.LINK_DB_LOG)
    suspend fun signIn(@Body user:Userdto): Response<LoginResponse>

    @POST(BuildConfig.LINK_DB_REG)
    suspend fun signUP(@Body user:Registerdto): Response<String>

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

