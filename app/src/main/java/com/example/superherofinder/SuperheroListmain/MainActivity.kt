package com.example.superherofinder.SuperheroListmain

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherofinder.*
import com.example.superherofinder.Favoritos.FavoritosActivity
import com.example.superherofinder.Historial.HistorialActivity
import com.example.superherofinder.Login.LoginActivity
import com.example.superherofinder.MiEquipo.MiEquipoActivity
import com.example.superherofinder.ModificarUsuario.ModificarUserActivity
import com.example.superherofinder.SuperheroDetails.DetailsHeroActivity
import com.example.superherofinder.SuperheroDetails.DetailsHeroActivity.Companion.EXTRA_ID
import com.example.superherofinder.SuperheroListmain.RecyclerView.SuperHeroesAdapter
import com.example.superherofinder.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperHeroesAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var tokenManager: TokenManager
    private lateinit var userActual:UserDetails
    private lateinit var userManager: UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userManager= UserManager(this)
        tokenManager = TokenManager(this)
        retrofit = getRetrofit()
        isConfirm()
        initUI()

    }

    private fun initUI() {
        establecerPantallaPrincipal()
        initDrawer()
        binding.serchSuperhero.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchByName(newText)
                return false
            }
        }
        )
        adapter = SuperHeroesAdapter(onItemSelected = { navigateDetails(it) })
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter

    }

    private fun searchByName(aBuscar: String?) {
        binding.progressBar.isVisible = true
        var parameter = aBuscar
        if (parameter == null) {
            parameter = "a"
        }
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getSuperHeroes(parameter)
            if (myResponse.isSuccessful) {
                Log.i("consulting", "Funciona :)")
                val response: SuperHeroesDataResponse? = myResponse.body()
                if (response != null) {
                    runOnUiThread {

                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false

                    }
                } else {
                    Log.i("consulting", "No funciona:)")
                }
            } else {
                Log.i("consulting", "No funciona:)")

            }
        }
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun establecerPantallaPrincipal() {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(ApiService::class.java).getSuperHeroes("a")
            if (myResponse.isSuccessful) {
                val response: SuperHeroesDataResponse? = myResponse.body()
                if (response != null) {
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        adapter.listDefault = response.superheroes
                        binding.progressBar.isVisible = false

                    }
                }
            }
        }
    }
    private fun getRetrofitBdd(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateDetails(id: String) {
        val intent = Intent(this, DetailsHeroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
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

    private fun initDrawer() {

        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView
        val menuItem = navigationView.menu.findItem(R.id.nav_item8)
        cambiarColorItem(menuItem, android.R.color.holo_red_dark)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menuicon)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Aquí puedes manejar las acciones de los elementos del menú
                R.id.nav_item1 -> {
                    val intent = Intent(this@MainActivity, FavoritosActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_item4 -> {
                    val intent = Intent(this@MainActivity, ModificarUserActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_item5 -> {
                    val intent = Intent(this@MainActivity, HistorialActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_item6 -> {
                    val intent = Intent(this@MainActivity, MiEquipoActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_item7 -> {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    tokenManager.clearToken()
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    true
                }

                R.id.nav_item8 -> {
                    sendConfirmationEmial()
                    true
                }
                // Agregar más casos según sea necesario
                else -> false
            }
        }
    }

    private fun cambiarColorItem(menuItem: MenuItem?, color: Int) {
        val spannableTitle = SpannableString(menuItem?.title)
        spannableTitle.setSpan(
            ForegroundColorSpan(resources.getColor(color)),
            0,
            spannableTitle.length,
            0
        )
        menuItem?.title = spannableTitle
    }

    private fun setDataHeader(correo: String, name: String) {
        val header = navigationView.getHeaderView(0)
        header.findViewById<TextView>(R.id.tv_Nombre).text = name
        header.findViewById<TextView>(R.id.tv_Correo).text = correo
    }

    private fun dialog(title:String,message:String,onAccept:()->Unit) {
        val dialog = MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, which ->
                onAccept()
            }.show()
    }

    private fun isConfirm(){
        val retrofitBdd=getRetrofitBdd()
        val service = retrofitBdd.create(BdInterface::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getActualUser(tokenManager.getToken()!!)
            if (response.isSuccessful){
                userActual=response.body()!!.userDetails
                userManager.saveUser(userActual)

                runOnUiThread {
                        userManager.getUser()?.let { setDataHeader(it.email, it.nombre) }
                        if (userActual.confirmado){
                            val menuItem = navigationView.menu.findItem(R.id.nav_item8)
                            menuItem.isVisible=false;
                        }
                }
            }else{
                Log.i("Data","${response.errorBody()} ")
            }
        }
    }

    private fun sendConfirmationEmial(){
        val service = getRetrofitBdd().create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val emailMap = mapOf("email" to userActual.email)
            val response = service.getConfirmEmail(tokenManager.getToken()!!, emailMap)
            if (response.isSuccessful) {
                // La solicitud fue exitosa
                runOnUiThread {
                    dialog("Confirmation", "El email se envio correctamente") {}
                }
            } else {
                // La solicitud no fue exitosa
                runOnUiThread {
                    dialog("Confirmation", "El email se envio hace poco tiempo, revisa tu bandeja de spam") {}
                }
            }
        }
    }


}