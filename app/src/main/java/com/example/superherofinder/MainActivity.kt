package com.example.superherofinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherofinder.DetailsHeroActivity.Companion.EXTRA_ID
import com.example.superherofinder.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var retrofit:Retrofit
    private lateinit var adapter: SuperHeroesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit=getRetrofit()
        initUI()
    }

    private fun initUI() {
        establecerPantallaPrincipal()

        binding.serchSuperhero.setOnQueryTextListener(object :SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        )
        adapter= SuperHeroesAdapter(){navigateDetails(it)}
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter=adapter

    }

    private fun searchByName(aBuscar:String) {
        binding.progressBar.isVisible=true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getSuperHeroes(aBuscar)
            if (myResponse.isSuccessful){
                Log.i("consulting","Funciona :)")
                val response:SuperHeroesDataResponse? = myResponse.body()
                if (response != null){
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible=false

                    }
                }
            }else Log.i("consulting","No funciona:)")
        }
    }
    private fun getRetrofit(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun establecerPantallaPrincipal(){
        binding.progressBar.isVisible=true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse=retrofit.create(ApiService::class.java).getSuperHeroes("a")
            if(myResponse.isSuccessful){
                val response:SuperHeroesDataResponse? = myResponse.body()
                if (response != null){
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible=false

                    }
                }
            }
        }
    }

    private fun navigateDetails(id:String){
        val intent = Intent(this,DetailsHeroActivity::class.java)
        intent.putExtra(EXTRA_ID,id)
        startActivity(intent)
    }


}