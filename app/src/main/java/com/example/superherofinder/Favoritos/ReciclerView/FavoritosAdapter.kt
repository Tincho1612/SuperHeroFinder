package com.example.superherofinder.Favoritos.ReciclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.R
import com.example.superherofinder.SuperHeroDetailsResponse
import com.example.superherofinder.SuperHeroItemResponse
import com.example.superherofinder.SuperheroListmain.RecyclerView.SuperHeroesViewHolder

class FavoritosAdapter(private var listaHeroes:ArrayList<SuperHeroDetailsResponse>):RecyclerView.Adapter<FavoritosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.itemfavhero,parent,false)
        return FavoritosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaHeroes.size
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        holder.render(listaHeroes[position]) {listaHeroes.removeAt(position)
            notifyItemRemoved(position)
        }
    }


}