package com.example.superherofinder.Historial.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.Favoritos.ReciclerView.FavoritosViewHolder
import com.example.superherofinder.Pelea
import com.example.superherofinder.R
import com.example.superherofinder.SuperHeroesDataResponse

class PeleaAdapter(val peleas: ArrayList<Pelea>): RecyclerView.Adapter<PeleaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeleaViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_pelea,parent,false)
        return PeleaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return peleas.size;
    }

    override fun onBindViewHolder(holder: PeleaViewHolder, position: Int) {
        holder.render(peleas[position])
    }



}