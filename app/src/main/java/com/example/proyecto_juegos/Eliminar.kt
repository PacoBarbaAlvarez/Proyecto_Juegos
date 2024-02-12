package com.example.proyecto_juegos

import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_juegos.databinding.ActivityEliminarBinding
import com.google.firebase.firestore.FirebaseFirestore

// Clase Eliminar que extiende de ActivityWithMenus
class Eliminar : ActivityWithMenus() {

    // Variables para manejar la vista y la base de datos de Firestore
    private lateinit var binding: ActivityEliminarBinding
    private val db = FirebaseFirestore.getInstance()

    // Método onCreate para inicializar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad y establecerlo como contenido de la vista
        binding = ActivityEliminarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el clic del botón "Eliminar"
        binding.bEliminar.setOnClickListener {
            // Obtener el ID del producto a eliminar
            val id = binding.id.text.toString()

            // Verificar si se ingresó un ID válido
            if (id.isNotEmpty()) {
                // Eliminar el producto de Firestore por su ID
                db.collection("Productos").document(id)
                    .delete()
                    .addOnSuccessListener {
                        // Mostrar mensaje de éxito y limpiar los campos
                        Toast.makeText(
                            this,
                            "Producto eliminado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearFields()
                    }
                    .addOnFailureListener { e ->
                        // Mostrar mensaje de error si falla la operación en Firestore
                        Toast.makeText(
                            this,
                            "Error al eliminar producto: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                // Mostrar mensaje si no se ingresó un ID
                Toast.makeText(
                    this,
                    "Por favor, ingresa el ID del producto a eliminar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Método para limpiar el campo de entrada del ID
    private fun clearFields() {
        binding.id.text.clear()
    }
}
