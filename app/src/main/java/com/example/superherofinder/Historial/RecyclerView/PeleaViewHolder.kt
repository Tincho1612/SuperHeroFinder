package com.example.superherofinder.Historial.RecyclerView

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.superherofinder.ApiService
import com.example.superherofinder.Pelea
import com.example.superherofinder.SuperHeroesDataResponse
import com.example.superherofinder.databinding.ItemPeleaBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PeleaViewHolder(view:View): RecyclerView.ViewHolder(view) {
    private val binding = ItemPeleaBinding.bind(view)
    private val mainHandler = Handler(Looper.getMainLooper())
    private val retrofit = Retrofit
                    .Builder()
                    .baseUrl("https://superheroapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    fun render(pelea:Pelea){
        getHeroe(pelea)
    }

    fun getHeroe(pelea: Pelea){
        val service=retrofit.create(ApiService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response1=service.getHeroById(pelea.idheroe1.toString())
            val response2=service.getHeroById(pelea.idheroe2.toString())
            if (response1.isSuccessful && response2.isSuccessful){
                mainHandler.post {
                    binding.tvFechaPelea.text=pelea.fecha
                    binding.tuHeroe.text=response1.body()!!.superHeroname
                    binding.tvEnemiName.text=response2.body()!!.superHeroname
                    Picasso.get().load(response1.body()!!.imagen.url).into(binding.ivTuHeroe)
                    Picasso.get().load(response2.body()!!.imagen.url).into(binding.ivHeroeEnemigo)
                    if (pelea.idGanador==response1.body()!!.id.toInt()){
                        binding.tvHeroeGanador.text= response1.body()!!.superHeroname
                    }else{
                        binding.tvHeroeGanador.text=response2.body()!!.superHeroname
                    }
                }
            }else{
                Log.i("Pelea","No Funciona")
            }
        }
    }
}

