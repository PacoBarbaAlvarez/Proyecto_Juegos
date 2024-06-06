package com.example.proyecto_juegos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_juegos.databinding.ActivityModificarBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class Modificar : ActivityWithMenus() {

    private lateinit var binding: ActivityModificarBinding
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener la ID del producto de los extras del Intent
        val productId = intent.getStringExtra("productId")

        // Click listener para seleccionar imagen
        binding.imagencard.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Click listener para el botón de modificar
        binding.modificar.setOnClickListener {
            val id = binding.id.text.toString().trim() // Obtener el valor del campo de ID
            val nombre = binding.nombre.text.toString().trim()
            val descripcion = binding.descripcion.text.toString().trim()
            val precio = binding.precio.text.toString().trim()

            // Verificar que los campos obligatorios no estén vacíos
            if (!id.isNullOrEmpty() && nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty()) {
                // Actualizar los datos del producto en Firestore
                db.collection("Productos").document(id)
                    .set(
                        mapOf(
                            "Nombre" to nombre,
                            "Descripcion" to descripcion,
                            "Precio" to precio
                        ),
                        // Asegúrate de que la operación de escritura reemplace todos los campos en el documento existente
                        SetOptions.merge()
                    )
                    .addOnSuccessListener {
                        // Mostrar un mensaje Toast si la modificación fue exitosa
                        Toast.makeText(
                            this,
                            "Producto modificado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Si se ha seleccionado una imagen, subirla y luego actualizar la URL de la imagen en Firestore
                        if (selectedImageUri != null) {
                            subirNuevaImagenAFirebase(id)
                        } else {
                            // Limpiar los campos del formulario después de la modificación
                            clearFields()
                        }
                    }
                    .addOnFailureListener { e ->
                        // Mostrar un mensaje Toast si ocurrió un error durante la modificación
                        Toast.makeText(
                            this,
                            "Error al modificar producto: $e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                // Mostrar un mensaje Toast si algún campo obligatorio está vacío o la ID es nula
                Toast.makeText(
                    this,
                    "Por favor, completa todos los campos obligatorios correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Método para manejar el resultado de la selección de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            // Cargar la imagen seleccionada en el ImageView
            binding.imagencard.setImageURI(selectedImageUri)
        }
    }

    // Método para subir la nueva imagen a Firebase Storage
    private fun subirNuevaImagenAFirebase(productId: String) {
        selectedImageUri?.let { uri ->
            val nombreImagen = UUID.randomUUID().toString()
            val referenciaImagen = storageRef.child("imagenes/$nombreImagen")

            referenciaImagen.putFile(uri)
                .addOnSuccessListener {
                    // Operación de subida exitosa
                    referenciaImagen.downloadUrl.addOnSuccessListener { url ->
                        // Actualizar la URL de la imagen en Firestore
                        db.collection("Productos").document(productId)
                            .update("Imagen", url.toString())
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Imagen modificada correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Limpiar los campos del formulario después de la modificación
                                clearFields()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    this,
                                    "Error al actualizar la URL de la imagen: $e",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al subir la imagen: $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    // Método para limpiar los campos del formulario
    private fun clearFields() {
        binding.nombre.text.clear()
        binding.id.text.clear()
        binding.descripcion.text.clear()
        binding.precio.text.clear()
        // Limpiar la imagen del ImageView
        binding.imagencard.setImageDrawable(null)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
