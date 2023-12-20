package com.example.superherofinder.SuperheroDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.superherofinder.*
import com.example.superherofinder.databinding.ActivityDetailsHeroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailsHeroActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID="extra_id"
    }
    private lateinit var binding:ActivityDetailsHeroBinding
    private lateinit var tokenManager:TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tokenManager= TokenManager(this)
        val id= intent.getStringExtra(EXTRA_ID).orEmpty()
        binding.progressBar.isVisible=true
        getSuperHeroInformation(id)
        binding.btnAgregarFavorito.setOnClickListener {
            agregarFav(id)
        }

    }

    private fun agregarFav(id: String) {
        val retrofit=getRetrofitBdd()
        val service=retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response=service.agregarFav(id.toInt(),tokenManager.getToken()!!)
            if (response.isSuccessful){
                Log.i("AgregarFav","FUnciona ${response.body()}")
            }else{
                Log.i("AgregarFav","No FUnciona ${response}")
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
    private fun getRetrofitBdd(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getSuperHeroInformation(id: String) {
        binding.progressBar.isVisible=true
        CoroutineScope(Dispatchers.IO).launch {
            val details=getRetrofit().create(ApiService::class.java).getHeroById(id)
            if (details.body()!=null){
                runOnUiThread { initUI(details.body()!!)
                    binding.progressBar.isVisible=false
                }
            }
        }


    }

    private fun initUI(body: SuperHeroDetailsResponse) {
        Log.i("Imagen","URL:${body.imagen.url}")
        binding.tvPublisher.text=body.biografia.publisher.orEmpty()
        binding.tvSuperHeroName.text=body.superHeroname
        Picasso.get().load(body.imagen.url).into(binding.ivSuperHero)
        updateHeight(binding.viewIntelligence,body.superHeroStats.inteligencia)
        updateHeight(binding.viewCombat,body.superHeroStats.combate)
        updateHeight(binding.viewDurability,body.superHeroStats.resistencia)
        updateHeight(binding.viewPower,body.superHeroStats.poder)
        updateHeight(binding.viewSpeed,body.superHeroStats.velocidad)
        updateHeight(binding.viewStrength,body.superHeroStats.fuerza)

    }

    private fun updateHeight(view:View,altura:String){
        if (altura=="null"){
            val params = view.layoutParams
            params.height= pxToDp(5.toFloat())
            view.layoutParams=params
        }else{
            val params = view.layoutParams
            params.height= pxToDp(altura.toFloat())
            view.layoutParams=params
        }




    }

    private fun pxToDp(toFloat: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,toFloat,resources.displayMetrics).roundToInt()
    }


}