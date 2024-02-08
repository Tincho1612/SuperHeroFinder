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
        setStats(heroe)
        binding.btnDelete.setOnClickListener {
            onClick(heroe.id.toInt())
        }
    }

    fun setStats(item: SuperHeroDetailsResponse){
        if (item.superHeroStats.combate=="null"){
            binding.cantidadCombate.text="5"
        }else{
            binding.cantidadCombate.text=item.superHeroStats.combate
        }
        if (item.superHeroStats.fuerza=="null"){
            binding.cantidadFuerza.text="5"
        }else{
            binding.cantidadFuerza.text=item.superHeroStats.fuerza
        }
        if (item.superHeroStats.poder=="null"){
            binding.cantidadPoder.text="5"
        }else{
            binding.cantidadPoder.text=item.superHeroStats.poder
        }
        if (item.superHeroStats.inteligencia=="null"){
            binding.cantidadInteligencia.text="5"
        }else{
            binding.cantidadInteligencia.text=item.superHeroStats.inteligencia
        }
        if (item.superHeroStats.velocidad=="null"){
            binding.cantidadVelocidad.text="5"
        }else{
            binding.cantidadVelocidad.text=item.superHeroStats.velocidad
        }
        if (item.superHeroStats.resistencia=="null"){
            binding.cantidadResistencia.text="5"
        }else{
            binding.cantidadResistencia.text=item.superHeroStats.resistencia
        }

    }
}