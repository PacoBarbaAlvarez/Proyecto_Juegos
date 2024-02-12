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

        // Configurar el OnClickListener para el botón de registrar
        binding.bregistrar.setOnClickListener{
            val db= FirebaseFirestore.getInstance()

            // Comprobar que los campos no están vacíos
            if(binding.Nombre.text.isNotEmpty()  && binding.Correo.text.isNotEmpty() && binding.ContraseA.text.isNotEmpty())
            {
                // Crear un usuario con correo y contraseña proporcionados por el usuario
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.Correo.text.toString(), binding.ContraseA.text.toString()
                )
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            // Si el registro es exitoso, guardar el nombre del usuario en Firestore
                            db.collection("usuarios").document(binding.Correo.text.toString())
                                .set(mapOf(
                                    "nombre" to binding.Nombre.text.toString(),
                                ))

                            // Iniciar la actividad del listado de productos después del registro
                            startActivity(Intent(this, Listado::class.java))
                        }
                        else{
                            // Mostrar un mensaje Toast si no se pudo registrar el usuario
                            Toast.makeText(this,"No se ha  podido registrar el usuario", Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else {
                // Mostrar un mensaje Toast si algún campo está vacío
                Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_LONG).show()
            }
        }

    }
}
