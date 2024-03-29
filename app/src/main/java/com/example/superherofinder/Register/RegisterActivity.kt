package com.example.superherofinder.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.superherofinder.BdInterface
import com.example.superherofinder.Login.LoginActivity
import com.example.superherofinder.Registerdto
import com.example.superherofinder.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    lateinit var  binding:ActivityRegisterBinding
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://servidor-superherogame.vercel.app/api/") // Reemplaza "base_url" con la URL base de tu servidor
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI(){

        binding.tvLoguearse.setOnClickListener {
            navigateToLogin()
        }
        btnRegistrarse()
    }

    private fun btnRegistrarse() {

        binding.btnRegister.setOnClickListener {
           registrarse()
        }
    }
    private fun dialog(title:String,message:String,onAccept:()->Unit) {
        val dialog = AlertDialog.Builder(this).setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { _, _ ->
                onAccept()
            }
            .setCancelable(false)


        dialog.show()
    }

    private fun registrarse(){
        val service = retrofit.create(BdInterface::class.java)
        if (validarFormRegister()){
            val user= Registerdto(binding.etNombreRegister.text.toString(),binding.etApellidoRegister.text.toString(),binding.etCorreoRegister.text.toString(),binding.etContraseniaRegister.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.signUP(user)
                if (response.isSuccessful){
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Te registraste correctamente", Toast.LENGTH_LONG).show()
                        dialog("Register","Te registraste correctamente, se te envio un email a tu cuenta asociada y se te otorgaron 5 heroes en tu equipo de regalo"){navigateToLogin()}

                    }
                }else{
                    runOnUiThread {
                        Log.i("Register","${response.errorBody()}")
                        Toast.makeText(this@RegisterActivity, "El nombre o apellido tienen numeros o el servidor falla", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validarFormRegister():Boolean{
        if (binding.etApellidoRegister.text.isEmpty() || binding.etNombreRegister.text.isEmpty()||binding.etCorreoRegister.text.isEmpty()|| binding.etContraseniaRegister.text.isEmpty()){
            Toast.makeText(this@RegisterActivity, "No puedes dejar datos vacios", Toast.LENGTH_SHORT).show()
            return false
        }else if (!(binding.etCorreoRegister.text.toString().contains("@"))){
            Toast.makeText(this, "El formato del email es invalido", Toast.LENGTH_SHORT).show()
            return false
        }else if (binding.etContraseniaRegister.text.toString().length<8){
            Toast.makeText(this, "la contraseña debe tener mas de 8 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.etNombreRegister.text.toString().length<4 || binding.etApellidoRegister.text.toString().length<4){
            Toast.makeText(this, "El apellido o nombre es invalido", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}

