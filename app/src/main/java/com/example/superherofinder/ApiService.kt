package com.example.superherofinder

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/${BuildConfig.API_KEY}/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superheroName:String):Response<SuperHeroesDataResponse>

    @GET("/api/${BuildConfig.API_KEY}/{id}")
    suspend fun getHeroById(@Path("id") superHeroId:String):Response<SuperHeroDetailsResponse>

}