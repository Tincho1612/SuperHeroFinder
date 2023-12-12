package com.example.superherofinder

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val accesToken:String

)