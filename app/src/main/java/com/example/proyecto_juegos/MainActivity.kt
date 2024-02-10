package com.example.proyecto_juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_juegos.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    public lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.iniciarSesion.setOnClickListener{
            login()
        }

        binding.registrar.setOnClickListener {
            registro()
        }
    }

    private fun registro() {
        startActivity(Intent(this, Registrar::class.java))
    }

    private fun login(){
        //Comprobamos que los campos de correo y campos no esten vacios
        if(binding.email.text.isNotEmpty() && binding.contraseA.text.isNotEmpty()){
            // Iniciamos sesion con el metodo signIn y enviamos a Firebase el correo y la contraseña
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.contraseA.text.toString()
            )

                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(this,Listado::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Correo o password incorrecto", Toast.LENGTH_LONG).show()
                    }
                }
        }
        else{
            Toast.makeText(this,"Algun campo esta vacio", Toast.LENGTH_LONG).show()
        }
    }
}