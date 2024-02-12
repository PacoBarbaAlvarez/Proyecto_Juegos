package com.example.proyecto_juegos

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.example.proyecto_juegos.databinding.ActivityAnadirBinding
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast

// Clase Anadir que extiende de ActivityWithMenus
class Anadir : ActivityWithMenus() {

    // Variables para manejar la vista y la base de datos de Firestore
    private lateinit var binding: ActivityAnadirBinding
    private val db = FirebaseFirestore.getInstance()

    // Método onCreate para inicializar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad y establecerlo como contenido de la vista
        binding = ActivityAnadirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el clic del botón "Insertar"
        binding.Insertar.setOnClickListener {
            // Obtener los valores de los campos de entrada
            val id = binding.id.text.toString()
            val nombre = binding.nombre.text.toString()
            val descripcion = binding.descripcion.text.toString()
            val precio = binding.precio.text.toString()

            // Mostrar los valores en el registro (log)
            Log.d("Anadir", "Nombre: $nombre, ID: $id, Descripcion: $descripcion, Precio: $precio")

            // Verificar si los campos no están vacíos
            if (id.isNotEmpty() && nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                // Guardar los datos en Firestore
                db.collection("Productos").document(id).set(
                    mapOf(
                        "nombre" to nombre,
                        "descripcion" to descripcion,
                        "precio" to precio
                    )
                ).addOnSuccessListener {
                    // Mostrar mensaje de éxito y limpiar los campos
                    Toast.makeText(
                        this,
                        "Producto agregado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    clearFields()
                }.addOnFailureListener { e ->
                    // Mostrar mensaje de error si falla la operación en Firestore
                    Toast.makeText(
                        this,
                        "Error al agregar producto: $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Mostrar mensaje si no se completaron todos los campos
                Toast.makeText(
                    this,
                    "Por favor, completa todos los campos correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Método para limpiar los campos de entrada
    private fun clearFields(){
        binding.nombre.text.clear()
        binding.id.text.clear()
        binding.descripcion.text.clear()
        binding.precio.text.clear()
    }
}
