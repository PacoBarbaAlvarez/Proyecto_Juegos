package com.example.proyecto_juegos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_juegos.adapter.ProductoAdapter
import com.example.proyecto_juegos.databinding.ActivityListadoBinding
import com.google.firebase.firestore.FirebaseFirestore

// Clase Listado que extiende de ActivityWithMenus
class Listado : ActivityWithMenus() {

    // Variables para manejar la vista, la base de datos de Firestore y el adaptador del RecyclerView
    private lateinit var binding: ActivityListadoBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var productoAdapter: ProductoAdapter

    // Método onCreate para inicializar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad y establecerlo como contenido de la vista
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del RecyclerView
        binding.recycler.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(emptyList()) // Adaptador inicialmente vacío
        binding.recycler.adapter = productoAdapter

        // Obtener los datos de Firebase
        obtenerDatosFirebase()
    }

    // Método para obtener los datos de Firebase y actualizar el adaptador del RecyclerView
    private fun obtenerDatosFirebase() {
        db.collection("Productos")
            .get()
            .addOnSuccessListener { result ->
                val productosList = mutableListOf<Productos>()
                for (document in result) {
                    // Obtener nombre, descripción y precio del documento y crear un objeto Productos
                    val nombre = document.getString("nombre") ?: ""
                    val descripcion = document.getString("descripcion") ?: ""
                    val precio = document.getString("precio") ?: ""
                    val producto = Productos(nombre, descripcion, precio)
                    productosList.add(producto) // Agregar el producto a la lista
                }
                // Actualizar el adaptador con los datos obtenidos
                productoAdapter.actualizarOfertas(productosList)
            }
            .addOnFailureListener { exception ->
                // Manejar errores y mostrar un Toast en caso de error
                Toast.makeText(this, "Error al obtener los datos: $exception", Toast.LENGTH_SHORT).show()
            }
    }
}
