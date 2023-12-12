package com.example.superherofinder

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface bdInterface {
    @POST(BuildConfig.LINK_DB_LOG)
    suspend fun postUser(@Path("name") superheroName:String): Response<LoginResponse>
}


