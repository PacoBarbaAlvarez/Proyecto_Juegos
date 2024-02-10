package com.example.proyecto_juegos
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_juegos.databinding.ActivityModificarBinding
import com.google.firebase.firestore.FirebaseFirestore
class Modificar : ActivityWithMenus(){

    private lateinit var binding: ActivityModificarBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.modificar.setOnClickListener {
            val id = binding.id.text.toString()
            val nombre = binding.nombre.text.toString()
            val descripcion = binding.descripcion.text.toString()
            val precio = binding.precio.text.toString()

            if (id.isNotEmpty() && nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                db.collection("Productos").document(id)
                    .update(
                        mapOf(
                            "nombre" to nombre,
                            "descripcion" to descripcion,
                            "precio" to precio
                        )
                    )
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Producto modificado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearFields()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al modificar producto: $e",
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

    private fun clearFields() {
        binding.nombre.text.clear()
        binding.id.text.clear()
        binding.descripcion.text.clear()
        binding.precio.text.clear()
    }
}