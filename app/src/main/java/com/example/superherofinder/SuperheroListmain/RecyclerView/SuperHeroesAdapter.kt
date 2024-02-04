package com.example.superherofinder.SuperheroListmain.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.R
import com.example.superherofinder.SuperHeroItemResponse
import com.example.superherofinder.SuperheroListmain.RecyclerView.SuperHeroesViewHolder

class SuperHeroesAdapter(private var superHeroes:List<SuperHeroItemResponse> = emptyList(), private val onItemSelected:(String)->Unit, var listDefault:List<SuperHeroItemResponse> = emptyList()) : RecyclerView.Adapter<SuperHeroesViewHolder>() {
    fun updateList(superHeroes:List<SuperHeroItemResponse>?){
        if (superHeroes!=null){
            this.superHeroes=superHeroes
        }else{
            this.superHeroes=listDefault
        }
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