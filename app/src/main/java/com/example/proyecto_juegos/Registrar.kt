package com.example.proyecto_juegos

import android.os.Bundle
import com.example.proyecto_juegos.databinding.ActivityRegistrarBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.proyecto_juegos.Listado
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registrar : AppCompatActivity() {

    lateinit var binding: ActivityRegistrarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bregistrar.setOnClickListener{
            val db= FirebaseFirestore.getInstance()

            // COMPROBAMOS Q NO ESTA VACIO
            if(binding.Nombre.text.isNotEmpty()  && binding.Correo.text.isNotEmpty() && binding.ContraseA.text.isNotEmpty())
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.Correo.text.toString(), binding.ContraseA.text.toString()
                )
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            db.collection("usuarios").document(binding.Correo.text.toString())
                                .set(mapOf(
                                    "nombre" to binding.Nombre.text.toString(),
                                ))

                            startActivity(Intent(this, Listado::class.java))
                        }
                        else{
                            Toast.makeText(this,"No se ha  podido registrar el usuario", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else {
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_LONG).show()
            }
        }

    }
}
