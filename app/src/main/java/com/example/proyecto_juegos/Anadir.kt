package com.example.proyecto_juegos

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.proyecto_juegos.databinding.ActivityAnadirBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class Anadir : ActivityWithMenus() {

    private lateinit var binding: ActivityAnadirBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.imagencard.setImageURI(uri)
            selectedImageUri = uri
        } else {
            Log.e("Anadir", "Error al seleccionar la imagen")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnadirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        binding.Insertar.setOnClickListener {
            val id = binding.id.text.toString()
            val nombre = binding.nombre.text.toString()
            val descripcion = binding.descripcion.text.toString()
            val precio = binding.precio.text.toString()

            if (id.isNotEmpty() && nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty() && selectedImageUri != null) {
                subirImagenAFirebase(selectedImageUri!!, id, nombre, descripcion, precio)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos correctamente y selecciona una imagen", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imagencard.setOnClickListener {
            pickMedia.launch("image/*")
        }
    }

    private fun subirImagenAFirebase(imageUri: Uri, id: String, nombre: String, descripcion: String, precio: String) {
        val nombreImagen = UUID.randomUUID().toString()
        val referenciaImagen = storageReference.child("Imagenes/$nombreImagen")

        referenciaImagen.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                obtenerUrlDescarga(referenciaImagen, id, nombre, descripcion, precio)
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "Error al subir la imagen: $exception")
                Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
            }
    }

    private fun obtenerUrlDescarga(referenciaImagen: StorageReference, id: String, nombre: String, descripcion: String, precio: String) {
        referenciaImagen.downloadUrl
            .addOnSuccessListener { urlDescarga ->
                almacenarProductoFirestore(id, nombre, descripcion, precio, urlDescarga.toString())
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseStorage", "Error al obtener la URL de descarga: $exception")
                Toast.makeText(this, "Error al obtener la URL de descarga", Toast.LENGTH_SHORT).show()
            }
    }

    private fun almacenarProductoFirestore(id: String, nombre: String, descripcion: String, precio: String, urlImagen: String) {
        db.collection("Productos")
            .whereEqualTo("Nombre", nombre)
            .whereEqualTo("Descripcion", descripcion) // Suponiendo que 'descripcion' sea equivalente a 'tamaño'
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    Toast.makeText(this, "Ya existe un producto con el mismo nombre y tamaño", Toast.LENGTH_SHORT).show()
                } else {
                    db.collection("Productos").document(id)
                        .set(mapOf(
                            "Nombre" to nombre,
                            "Descripcion" to descripcion, // Suponiendo que 'descripcion' sea equivalente a 'ingredientes'
                            "Precio" to precio,
                            "Imagen" to urlImagen
                        ))
                        .addOnSuccessListener {
                            Toast.makeText(this, "Producto almacenado correctamente", Toast.LENGTH_SHORT).show()
                            clearFields()
                        }
                        .addOnFailureListener { exception ->
                            Log.e("AnadirActivity", "Error al almacenar en Firestore: $exception")
                            Toast.makeText(this, "Error al almacenar el producto", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("AnadirActivity", "Error al consultar en Firestore: $exception")
                Toast.makeText(this, "Error al consultar el producto", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        binding.nombre.text.clear()
        binding.id.text.clear()
        binding.descripcion.text.clear()
        binding.precio.text.clear()
        binding.imagencard.setImageURI(null)
        selectedImageUri = null
    }
}
