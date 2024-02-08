package com.example.superherofinder

import com.example.superherofinder.Favoritos.FavoritosResponse
import com.example.superherofinder.Login.LoginResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BdInterface {
    @POST(BuildConfig.LINK_DB_LOG)
    suspend fun signIn(@Body user:Userdto): Response<LoginResponse>

    @POST(BuildConfig.LINK_DB_REG)
    suspend fun signUP(@Body user:Registerdto): Response<*>

    @GET("/api/user/favoritos")
    suspend fun getFavs(@Header("access-token") token:String):Response<FavoritosResponse>

    @PUT("/api/user/agregarFavorito/{heroeId}")
    suspend fun agregarFav(@Path("heroeId") heroeId: Int,@Header("access-token") token:String):Response<*>

    @DELETE("/api/user/eliminarFavorito/{heroeId}")
    suspend fun eliminarFav(
        @Path("heroeId") heroeId: Int,
        @Header("access-token") token: String
    ): Response<*>

    @PUT("/api/user/update")
    suspend fun changeEmail(@Body email: Emaildto,@Header("access-token") token: String):Response<*>

    @PUT("/api/user/update")
    suspend fun changePassword(@Body email: PassworDto,@Header("access-token") token: String):Response<*>

    @GET("/api/user/equipo")
    suspend fun getMiEquipo(@Header("access-token") token:String):Response<Equipo>

    @GET("/api/pelea/getPeleas")
    suspend fun getPeleas(@Header("access-token") token: String):Response<Peleas>

    @POST("/api/auth/requestConfirmationEmail")
    suspend fun getConfirmEmail(
        @Header("access-token") token: String,
        @Body body: Map<String, String>
    ): Response<*>

    @GET("/api/user/getActualUser")
    suspend fun getActualUser(@Header("access-token") token:String):Response<UserResponse>
}


data class UserResponse(
    @SerializedName("userResponse") val userDetails: UserDetails
)
data class UserDetails (
    @SerializedName("_id") val id:String,
    @SerializedName("nombre") val nombre:String,
    @SerializedName("apellido") val apellido:String,
    @SerializedName("email") var email:String,
    @SerializedName("favoritos") val favoritos:Array<Int>,
    @SerializedName("equipos") val equipos:Array<Int>,
    @SerializedName("historial") val historial:Array<String>,
    @SerializedName("confirmado") val confirmado:Boolean

)
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


data class Emaildto(
    @SerializedName("email") val email:String
)
data class PassworDto(
    @SerializedName("password") val password: String
    )

data class Pelea(
    @SerializedName("idHeroe1") val idheroe1:Int,
    @SerializedName("idHeroe2") val idheroe2:Int,
    @SerializedName("idGanador") val idGanador:Int,
    @SerializedName("fechaPelea") val fecha:String
)

data class Peleas(
    @SerializedName("historialCompleto") val peleas:ArrayList<Pelea>
)

data class Equipo(
    @SerializedName("message") val mensaje:String,
    @SerializedName("listaEquipo") val equipoIds:List<Int>
)





