package com.example.superherofinder.SuperheroListmain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherofinder.*
import com.example.superherofinder.Favoritos.FavoritosActivity
import com.example.superherofinder.SuperheroDetails.DetailsHeroActivity
import com.example.superherofinder.SuperheroDetails.DetailsHeroActivity.Companion.EXTRA_ID
import com.example.superherofinder.SuperheroListmain.RecyclerView.SuperHeroesAdapter
import com.example.superherofinder.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var retrofit:Retrofit
    private lateinit var adapter: SuperHeroesAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit=getRetrofit()
        initUI()
    }

    private fun initUI() {
        establecerPantallaPrincipal()
        initDrawer()
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
                val response: SuperHeroesDataResponse? = myResponse.body()
                if (response != null){
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible=false

                    }
                }else{
                    Log.i("consulting","No funciona:)")
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
                val response: SuperHeroesDataResponse? = myResponse.body()
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
        val intent = Intent(this, DetailsHeroActivity::class.java)
        intent.putExtra(EXTRA_ID,id)
        startActivity(intent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun initDrawer(){
        drawerLayout=binding.drawerLayout
        navigationView=binding.navigationView
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menuicon)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Aquí puedes manejar las acciones de los elementos del menú
                R.id.nav_item1 -> {
                    val intent=Intent(this@MainActivity, FavoritosActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_item2 -> {
                    // Lógica para el item 2
                    true
                }
                R.id.nav_item3 -> {

                    true
                 }
                R.id.nav_item4 -> {
                    true
                }
                // Agregar más casos según sea necesario
                else -> false
            }
        }
    }


}