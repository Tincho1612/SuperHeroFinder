package com.example.superherofinder.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.superherofinder.BdInterface
import com.example.superherofinder.Register.RegisterActivity
import com.example.superherofinder.SuperheroListmain.MainActivity
import com.example.superherofinder.TokenManager
import com.example.superherofinder.Userdto
import com.example.superherofinder.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://servidor-superherogame.vercel.app/api/") // Reemplaza "base_url" con la URL base de tu servidor
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        val shared= TokenManager(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (shared.getToken()!=null){
            navigateToMain()
        }
        initUI()



    }
    private suspend fun callApi(tokenManager: TokenManager, userdto: Userdto){
        val service = retrofit.create(BdInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response=service.signIn(userdto)
            if (response.isSuccessful){
                if (response.body()?.token!= null){
                    runOnUiThread {
                        tokenManager.saveToken(response.body()!!.token)
                        Toast.makeText(this@LoginActivity, "Se ah logueado correctamente", Toast.LENGTH_LONG).show()
                        navigateToMain()
                    }

                }

            }else{
                tokenManager.clearToken()
                println("Respuesta: ${response}")
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Algunos de los datos ingresados no son correctos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /*private fun callApi(tokenManager: TokenManager,correo:String,contraseña:String) {
        tokenManager.clearToken()
        val signInRequest = Userdto(correo,contraseña)
        val apiService = retrofit.create(BdInterface::class.java)

        apiService.signIn(signInRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.i("API3","Token:${response.body()?.token}")
                    // Guarda el token en SharedPreferences
                    val token = response.body()?.token
                        token?.let { tokenManager.saveToken(it) }
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)

                // Puedes realizar otras acciones con el token aquí si es necesario
                } else {
                    // Lanza una excepción en caso de error
                    tokenManager.clearToken()
                    Log.i("API3","algo mal salio${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Lanza una excepción en caso de error
                Log.i("API3","No se mando")
            }
        })
    }*/


    private fun initUI() {
        btnIngresar()
        btnRegistrarse()
    }

    private fun btnIngresar() {

        binding.btnIngresar.setOnClickListener {
            if (validarForm()){
                val user = Userdto(binding.etCorreo.text.toString(),binding.etContrasenia.text.toString())
                val tokenManager = TokenManager(this)

                CoroutineScope(Dispatchers.IO).launch {
                    callApi(tokenManager,user)
                }
            }



        }


    }

    private fun navigateToMain(){
        val intent=Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun validarForm():Boolean{
        if (binding.etCorreo.text.isEmpty() || binding.etContrasenia.text.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }else if(!(binding.etCorreo.text.toString().contains("@"))){
            Toast.makeText(this, "El formato de email no es valido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun btnRegistrarse(){
        binding.tvRegistrarse.setOnClickListener {
            navigateToRegister()
        }

    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}



