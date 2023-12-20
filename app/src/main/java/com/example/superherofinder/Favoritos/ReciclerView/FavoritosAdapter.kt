package com.example.superherofinder.Favoritos.ReciclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritosAdapter(private var listaHeroes:ArrayList<SuperHeroDetailsResponse>,private val context:Context):RecyclerView.Adapter<FavoritosViewHolder>() {
    private lateinit var tokenManager: TokenManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.itemfavhero,parent,false)
        return FavoritosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaHeroes.size
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        holder.render(listaHeroes[position]) {
            eliminarFavorito(listaHeroes[position].id.toInt(),position,listaHeroes)



        }
    }

    private fun eliminarFavorito(
        heroeId: Int,
        position: Int,
        listaHeroes: ArrayList<SuperHeroDetailsResponse>
    ){
        tokenManager= TokenManager(context)
        val retrofit = getRetrofit()
        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("Favoritos","Token:${tokenManager.getToken()}")
            val response = service.eliminarFav(heroeId,tokenManager.getToken()!!)
            if (response.isSuccessful){
                listaHeroes.remove(listaHeroes[position])
                notifyItemRemoved(position)
                Log.i("Favoritos","Salio todo bien")
            }else{
                Log.i("Favoritos","Error $response")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}