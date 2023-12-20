package com.example.superherofinder

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailsResponse(
    @SerializedName("name") val superHeroname: String,
    @SerializedName("powerstats") val superHeroStats: SuperHeroPowerStatsResponse,
    @SerializedName("image") val imagen:SuperHeroImageResponse,
    @SerializedName("biography") val biografia:Biography,
    @SerializedName("id") val id:String
)

data class SuperHeroPowerStatsResponse(
    @SerializedName("intelligence") val inteligencia: String,
    @SerializedName("strength") val fuerza: String,
    @SerializedName("speed") val velocidad: String,
    @SerializedName("durability") val resistencia: String,
    @SerializedName("power") val poder: String,
    @SerializedName("combat") val combate: String
)

data class Biography(
    @SerializedName("full-name") val nombreCompleto:String,
    @SerializedName("publisher") val publisher:String
)