package com.example.superherofinder

import com.google.gson.annotations.SerializedName

data class SuperHeroesDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperHeroItemResponse>
)

data class SuperHeroItemResponse(
    @SerializedName("id") val superHeroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superHeroImage:SuperHeroImageResponse

)

data class SuperHeroImageResponse(@SerializedName("url") val url:String)
