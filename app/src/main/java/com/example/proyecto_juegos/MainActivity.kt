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
        // Inflar el diseño de la actividad y establecerlo como contenido de la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Asignar un OnClickListener al botón de iniciar sesión
        binding.iniciarSesion.setOnClickListener{
            login() // Llamar al método login cuando se hace clic en el botón de iniciar sesión
        }

        // Asignar un OnClickListener al botón de registro
        binding.registrar.setOnClickListener {
            registro() // Llamar al método registro cuando se hace clic en el botón de registro
        }
    }

    // Método para iniciar el proceso de registro
    private fun registro() {
        startActivity(Intent(this, Registrar::class.java)) // Abrir la actividad de registro
    }

    // Método para iniciar sesión
    private fun login(){
        // Comprobar que los campos de correo y contraseña no estén vacíos
        if(binding.email.text.isNotEmpty() && binding.contraseA.text.isNotEmpty()){
            // Iniciar sesión con Firebase usando el correo y contraseña proporcionados
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.contraseA.text.toString()
            )
                .addOnCompleteListener{
                    if(it.isSuccessful){ // Verificar si el inicio de sesión fue exitoso
                        val intent = Intent(this,Listado::class.java)
                        startActivity(intent) // Abrir la actividad Listado si el inicio de sesión fue exitoso
                    }
                    else{
                        // Mostrar un mensaje Toast si el inicio de sesión fue fallido
                        Toast.makeText(this,"Correo o contraseña incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
        }
        else{
            // Mostrar un mensaje Toast si algún campo está vacío
            Toast.makeText(this,"Algun campo esta vacio", Toast.LENGTH_LONG).show()
        }
    }
}
