package com.example.superherofinder.SuperheroListmain.RecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.SuperHeroItemResponse
import com.example.superherofinder.databinding.ItemheroBinding
import com.squareup.picasso.Picasso

class SuperHeroesViewHolder (view:View): RecyclerView.ViewHolder(view) {
    private val binding= ItemheroBinding.bind(view)


    fun render(item: SuperHeroItemResponse, onItemSelected: (String) -> Unit){
        binding.tvSuperHeroName.text=item.name
        Picasso.get().load(item.superHeroImage.url).into(binding.ivSuperHero)
        binding.cardSuperHero.setOnClickListener { onItemSelected(item.superHeroId) }
    }
}