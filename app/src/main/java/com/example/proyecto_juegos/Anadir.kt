package com.example.proyecto_juegos

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.example.proyecto_juegos.databinding.ActivityAnadirBinding
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast

class Anadir : ActivityWithMenus() {

    private lateinit var binding: ActivityAnadirBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnadirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Insertar.setOnClickListener {
            val id = binding.id.text.toString()
            val nombre = binding.nombre.text.toString()
            val descripcion = binding.descripcion.text.toString()
            val precio = binding.precio.text.toString()

            Log.d("Anadir", "Nombre: $nombre, ID: $id, Descripcion: $descripcion, Precio: $precio")

            if (id.isNotEmpty() && nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                db.collection("Productos").document(id).set(
                mapOf(
                    "nombre" to nombre,
                    "descripcion" to descripcion,
                    "precio" to precio
                )
                ).addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Producto agregado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearFields()
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al agregar producto: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(
                    this,
                    "Por favor, completa todos los campos correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearFields(){
        binding.nombre.text.clear()
        binding.id.text.clear()
        binding.descripcion.text.clear()
        binding.precio.text.clear()
    }
}