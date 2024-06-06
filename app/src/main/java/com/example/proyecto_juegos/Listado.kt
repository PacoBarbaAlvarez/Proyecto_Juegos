package com.example.proyecto_juegos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_juegos.adapter.ProductoAdapter
import com.example.proyecto_juegos.databinding.ActivityListadoBinding
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

// Clase Listado que extiende de ActivityWithMenus
class Listado : ActivityWithMenus() {

    // Variables para manejar la vista, la base de datos de Firestore y el adaptador del RecyclerView
    private lateinit var binding: ActivityListadoBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var productoAdapter: ProductoAdapter
    private var productosList: MutableList<Productos> = mutableListOf()

    // Método onCreate para inicializar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad y establecerlo como contenido de la vista
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del RecyclerView
        binding.recycler.layoutManager = LinearLayoutManager(this)
        productoAdapter = ProductoAdapter(productosList) // Adaptador inicialmente vacío
        binding.recycler.adapter = productoAdapter

        // Agrega un listener al campo de filtro para realizar búsquedas dinámicas
        binding.filtro.addTextChangedListener { filtro ->
            val textoFiltro = filtro.toString() // Convertir el Editable a String
            productoAdapter.actualizarOfertas(productosList.filter { producto ->
                producto.Nombre.lowercase().contains(textoFiltro.lowercase())
            })
        }

        // Obtener los datos de Firebase
        cargarDatos()
    }

    private fun cargarDatos() {
        db.collection("Productos").get().addOnSuccessListener { carga ->
            productosList.clear()

            carga.forEach { document ->
                val producto = document.toObject(Productos::class.java)
                productosList.add(producto)
            }

            productoAdapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Log.e("Cargar", "Error en la obtención de productos", exception)
            Toast.makeText(this, "Error al obtener los datos: $exception", Toast.LENGTH_SHORT).show()
        }
    }
}
