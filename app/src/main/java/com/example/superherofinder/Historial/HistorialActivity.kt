package com.example.superherofinder.Historial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superherofinder.BdInterface
import com.example.superherofinder.Favoritos.ReciclerView.FavoritosAdapter
import com.example.superherofinder.Historial.RecyclerView.PeleaAdapter
import com.example.superherofinder.Pelea
import com.example.superherofinder.R
import com.example.superherofinder.TokenManager
import com.example.superherofinder.databinding.ActivityFavoritosBinding
import com.example.superherofinder.databinding.ActivityHistorialBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistorialActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHistorialBinding
    private lateinit var retrofit: Retrofit
    private lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit= getRetrofit()
        tokenManager= TokenManager(this)
        initUI()
    }

    private fun initUI(){
        getPeleas()
        /*binding.rvPeleas.setHasFixedSize(true)
        binding.rvPeleas.layoutManager = LinearLayoutManager(this)
        binding.rvPeleas.adapter=PeleaAdapter(listaPeleas)*/
    }

    private fun getPeleas(){
        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch{
            val response=service.getPeleas(tokenManager.getToken()!!)
            if (response.isSuccessful){
                Log.i("Peleas","Peleas:${response.body()}")
                if (response.body()==null){
                    runOnUiThread {
                        binding.progressBar.isVisible=false
                        binding.tvNoHayPeleas.isVisible=true
                    }

                }else{
                    runOnUiThread {
                        binding.rvPeleas.setHasFixedSize(true)
                        binding.rvPeleas.layoutManager = LinearLayoutManager(this@HistorialActivity)
                        binding.rvPeleas.adapter=PeleaAdapter(response.body()!!.peleas)
                        binding.progressBar.isVisible=false
                    }

                }
            }else{
                Log.i("Peleas","No funciona $response")
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}