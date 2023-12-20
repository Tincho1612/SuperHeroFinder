package com.example.superherofinder.ModificarUsuario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.superherofinder.*
import com.example.superherofinder.databinding.ActivityDetailsHeroBinding
import com.example.superherofinder.databinding.ActivityModificarUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModificarUserActivity : AppCompatActivity() {
    private lateinit var tokenManager: TokenManager
    private lateinit var binding: ActivityModificarUserBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityModificarUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tokenManager= TokenManager(this)
        retrofit=getRetrofit()
        initUI()

    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://servidor-superherogame.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initUI(){
        binding.buttonChangeEmail.setOnClickListener {
            if(validarFormCambiarCorreo()){
                changeEmail(binding.editTextEmail.text.toString())
            }
        }
        binding.buttonChangePassword.setOnClickListener {
            if(validarFormPassword()){
                changePassword(binding.editTextPassword.text.toString())
            }
        }
    }
    private fun changeEmail(email:String){
        val service=retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val respuesta=service.changeEmail(Emaildto(email),tokenManager.getToken()!!)
            if (respuesta.isSuccessful){
                Log.i("password","Funciona ")
                runOnUiThread {
                    Toast.makeText(this@ModificarUserActivity, "El Email se cambio correctamente", Toast.LENGTH_SHORT).show()
                }
            }else{
                Log.i("password","No Funciona ${respuesta}")
                runOnUiThread {
                    Toast.makeText(this@ModificarUserActivity, "Error al cambiar el Email", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun changePassword(password:String){
        val service=retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val respuesta=service.changePassword(PassworDto(password),tokenManager.getToken()!!)
            if (respuesta.isSuccessful){
                runOnUiThread {
                    Toast.makeText(this@ModificarUserActivity, "La contraseña se cambio correctamente", Toast.LENGTH_SHORT).show()
                }
            }else{
                Log.i("password","No Funciona ${respuesta}")
                runOnUiThread {
                    Toast.makeText(this@ModificarUserActivity, "Hubo un error al cambiar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun validarFormCambiarCorreo():Boolean{
        if (binding.editTextEmail.text.isEmpty() || binding.editTextConfirmEmail.text.isEmpty()){
            Toast.makeText(this@ModificarUserActivity, "No puedes dejar datos vacios", Toast.LENGTH_SHORT).show()
            return false
        }else if (!(binding.editTextEmail.text.toString().contains("@"))){
            Toast.makeText(this, "El formato del email es invalido", Toast.LENGTH_SHORT).show()
            return false
        }else if (binding.editTextEmail.text.toString()!=binding.editTextConfirmEmail.text.toString()){
            Toast.makeText(this, "Los mails son distintos", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validarFormPassword():Boolean{
        if (binding.editTextPassword.text.isEmpty() || binding.editTextConfirmPassword.text.isEmpty()){
            Toast.makeText(this@ModificarUserActivity, "No puedes dejar datos vacios", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.editTextPassword.text.toString()!=binding.editTextConfirmPassword.text.toString()){

            Toast.makeText(this@ModificarUserActivity, "La contraseña no es igual ", Toast.LENGTH_SHORT).show()
            return false
        }else if (binding.editTextPassword.text.toString().length<8){
            Toast.makeText(this@ModificarUserActivity, "la contraseña tiene menos de 8 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }
}