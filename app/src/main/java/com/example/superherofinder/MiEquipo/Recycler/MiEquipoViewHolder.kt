package com.example.superherofinder.MiEquipo.Recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.SuperHeroDetailsResponse
import com.example.superherofinder.databinding.ItemMiEquipoBinding
import com.squareup.picasso.Picasso

class MiEquipoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemMiEquipoBinding.bind(view)
    fun render(heroe: SuperHeroDetailsResponse,onClick:(Int)->Unit){
        binding.tvHeroName.text=heroe.superHeroname
        Picasso.get().load(heroe.imagen.url).into(binding.ivSuperHero)
        binding.cantidadCombate.text=heroe.superHeroStats.combate
        binding.cantidadFuerza.text=heroe.superHeroStats.fuerza
        binding.cantidadPoder.text=heroe.superHeroStats.poder
        binding.cantidadInteligencia.text=heroe.superHeroStats.inteligencia
        binding.cantidadVelocidad.text=heroe.superHeroStats.velocidad
        binding.cantidadResistencia.text=heroe.superHeroStats.resistencia
        binding.btnDelete.setOnClickListener {
            onClick(heroe.id.toInt())
        }
    }
}