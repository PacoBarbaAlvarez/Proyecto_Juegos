package com.example.proyecto_juegos
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_juegos.databinding.ActivityEliminarBinding
import com.google.firebase.firestore.FirebaseFirestore
class Eliminar : ActivityWithMenus(){

    private lateinit var binding: ActivityEliminarBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bEliminar.setOnClickListener {
            val id = binding.id.text.toString()

            if (id.isNotEmpty()) {
                // Eliminar el producto de Firestore por su ID
                db.collection("Productos").document(id)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Producto eliminado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearFields()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error al eliminar producto: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(
                    this,
                    "Por favor, ingresa el ID del producto a eliminar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun clearFields() {
        binding.id.text.clear()
    }
}