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

class FavoritosAdapter(private var listaHeroes:ArrayList<SuperHeroDetailsResponse>,private val context:Context,val onClickdelete:(Int,Int)->Unit):RecyclerView.Adapter<FavoritosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.itemfavhero,parent,false)
        return FavoritosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaHeroes.size
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        holder.render(listaHeroes[position],onClickdelete)
    }




}