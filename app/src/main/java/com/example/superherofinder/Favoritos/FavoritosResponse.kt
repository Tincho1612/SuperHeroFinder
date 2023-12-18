package com.example.superherofinder.Favoritos

import com.google.gson.annotations.SerializedName

data class FavoritosResponse (
    @SerializedName("message") val mensaje:String,
    @SerializedName("listaFavoritos") val superHeroesId:ArrayList<Int>
        )