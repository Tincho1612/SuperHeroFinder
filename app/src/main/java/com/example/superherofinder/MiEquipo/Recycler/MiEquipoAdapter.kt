package com.example.superherofinder.MiEquipo.Recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.Favoritos.ReciclerView.FavoritosViewHolder
import com.example.superherofinder.R
import com.example.superherofinder.SuperHeroDetailsResponse

class MiEquipoAdapter(private val heroes:List<SuperHeroDetailsResponse>,val onClick:(Int)->Unit):RecyclerView.Adapter<MiEquipoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiEquipoViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_mi_equipo,parent,false)
        return MiEquipoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    override fun onBindViewHolder(holder: MiEquipoViewHolder, position: Int) {
        holder.render(heroes[position],onClick)
    }

}