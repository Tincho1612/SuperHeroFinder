package com.example.superherofinder.Favoritos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.CorrectionInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherofinder.*
import com.example.superherofinder.Favoritos.ReciclerView.FavoritosAdapter
import com.example.superherofinder.databinding.ActivityFavoritosBinding
import com.example.superherofinder.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritosActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var tokenManager: TokenManager
    private lateinit var binding:ActivityFavoritosBinding
    private lateinit var listaHeroe:ArrayList<SuperHeroDetailsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        tokenManager = TokenManager(this)
        retrofit = getRetrofit() // Asigna la instancia de Retrofit a la propiedad de clase
        Log.i("Favoritos", "Se crea la activity")
        getFavoritos(tokenManager)
        Log.i("token","${tokenManager.getToken()}")
    }


    private fun getFavoritos(tokenManager: TokenManager) {
        binding.progressBar.isVisible=true
        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            if (tokenManager.getToken() != null) {
                val response = service.getFavs(tokenManager.getToken()!!)
                if (response.isSuccessful) {
                    Log.i("Favoritos", "Heroes:${response.body()?.superHeroesId}")
                    var listaHeroes= ArrayList<SuperHeroDetailsResponse>()
                    val retrofitHeroes=getRetrofitHeroes()
                    val serviceHeroes=retrofitHeroes.create(ApiService::class.java)
                    response.body()?.superHeroesId?.forEach { superHeroId ->
                        val responseHeroe=serviceHeroes.getHeroById(superHeroId.toString())
                         if (responseHeroe.isSuccessful){
                             listaHeroes.add(responseHeroe.body()!!)
                         }
                    }
                    runOnUiThread {
                        listaHeroe=listaHeroes
                        initRecycler(listaHeroe)
                    }



                } else {
                    Log.i("Favoritos", "La llamada fallo")
                }
            } else {
                Log.i("Favoritos", "No hay token")
            }
            runOnUiThread {
                binding.progressBar.isVisible=false
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun getRetrofitHeroes(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initRecycler(lista:ArrayList<SuperHeroDetailsResponse>){
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter=FavoritosAdapter(lista,this@FavoritosActivity) { position, id ->
            onItemdelete(
                position,
                id
            )
        }
    }

    private fun onItemdelete(position:Int,heroeId:Int){
        val retrofit = getRetrofit()
        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("Favoritos","Token:${tokenManager.getToken()}")
            Log.i("Favoritos","id:${heroeId} + $position")
            val response = service.eliminarFav(position,tokenManager.getToken()!!)
            if (response.isSuccessful){
                runOnUiThread {
                    listaHeroe.removeAt(heroeId)
                    binding.rvSuperhero.adapter?.notifyItemRemoved(heroeId)
                }


            }else{
                Log.i("Favoritos","Error $response")
            }
        }
    }


}