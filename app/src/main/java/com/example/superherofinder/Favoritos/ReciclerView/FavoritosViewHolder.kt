package com.example.superherofinder.Favoritos.ReciclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.SuperHeroDetailsResponse
import com.example.superherofinder.SuperHeroItemResponse
import com.example.superherofinder.databinding.ItemfavheroBinding
import com.example.superherofinder.databinding.ItemheroBinding
import com.squareup.picasso.Picasso

class FavoritosViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding= ItemfavheroBinding.bind(view)
    fun render(item: SuperHeroDetailsResponse,onClickDelete:(Int,Int)->Unit){
        binding.cantidadCombate.text=item.superHeroStats.combate
        binding.cantidadFuerza.text=item.superHeroStats.fuerza
        binding.cantidadPoder.text=item.superHeroStats.poder
        binding.cantidadInteligencia.text=item.superHeroStats.inteligencia
        binding.cantidadVelocidad.text=item.superHeroStats.velocidad
        binding.cantidadResistencia.text=item.superHeroStats.resistencia
        binding.tvHeroName.text=item.superHeroname
        Picasso.get().load(item.imagen.url).into(binding.ivSuperHero)
        binding.btnDelete.setOnClickListener {
            onClickDelete(item.id.toInt(),adapterPosition)
        }



    }
}