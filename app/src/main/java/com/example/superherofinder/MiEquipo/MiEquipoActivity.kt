package com.example.superherofinder.MiEquipo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherofinder.ApiService
import com.example.superherofinder.BdInterface
import com.example.superherofinder.MiEquipo.Recycler.MiEquipoAdapter
import com.example.superherofinder.R
import com.example.superherofinder.SuperHeroDetailsResponse
import com.example.superherofinder.TokenManager
import com.example.superherofinder.databinding.ActivityFavoritosBinding
import com.example.superherofinder.databinding.ActivityMiEquipoBinding
import com.example.superherofinder.databinding.ItemheroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MiEquipoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMiEquipoBinding
    private lateinit var retrofit: Retrofit
    private lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMiEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        retrofit=getRetrofitBdd()
        tokenManager = TokenManager(this)
        initUi()

    }

    private  fun initUi(){

        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response=service.getMiEquipo(tokenManager.getToken().toString())
            if (response.isSuccessful){
                var listaHeroes = ArrayList<SuperHeroDetailsResponse>()
                val retrofitHeroes=getRetrofitHeroes()
                val serviceHeroes=retrofitHeroes.create(ApiService::class.java)
                response.body()?.equipoIds?.forEach { equipoId ->
                    val responseHeroe=serviceHeroes.getHeroById(equipoId.toString())
                    if (responseHeroe.isSuccessful){
                        listaHeroes.add(responseHeroe.body()!!)
                    }


                }
                runOnUiThread {
                    Log.i("Debug","llegue ${listaHeroes}")
                    initRecycler(listaHeroes)
                }
            }
        }
    }

    private fun initRecycler(listaHeroes: ArrayList<SuperHeroDetailsResponse>) {
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter=MiEquipoAdapter(listaHeroes) { id -> onClickAddFav(id) }

    }

    private fun getRetrofitBdd():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitHeroes():Retrofit{
        return Retrofit
           .Builder()
           .baseUrl("https://superheroapi.com/api/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()
    }

    private fun onClickAddFav(id:Int){
        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response=service.agregarFav(id,tokenManager.getToken().toString())
            if (response.isSuccessful){
                runOnUiThread {
                    Toast.makeText(this@MiEquipoActivity,"Se añadio a favoritos correctamente",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@MiEquipoActivity,"Ocurrio un error al agregar el super heroe",Toast.LENGTH_LONG).show()
            }
        }
    }
}