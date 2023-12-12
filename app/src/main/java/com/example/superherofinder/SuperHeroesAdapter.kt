package com.example.superherofinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SuperHeroesAdapter(private var superHeroes:List<SuperHeroItemResponse> = emptyList(),private val onItemSelected:(String)->Unit) : RecyclerView.Adapter<SuperHeroesViewHolder>() {
    fun updateList(superHeroes:List<SuperHeroItemResponse>){
        this.superHeroes=superHeroes
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroesViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.itemhero,parent,false)
        return SuperHeroesViewHolder(view)
    }

    override fun getItemCount(): Int {
       return superHeroes.size
    }

    override fun onBindViewHolder(holder: SuperHeroesViewHolder, position: Int) {
        holder.render(superHeroes[position],onItemSelected)

    }

}