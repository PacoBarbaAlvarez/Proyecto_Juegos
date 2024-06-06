package com.example.proyecto_juegos.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_juegos.Productos
import com.example.proyecto_juegos.databinding.ItemListaBinding

// Clase que actúa como ViewHolder para el RecyclerView
class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Se asocia la vista del elemento de la lista mediante View Binding
    private val binding = ItemListaBinding.bind(view)

    // Método para renderizar los datos del producto en la vista
    fun render(ofertasModel: Productos) {
        // Se asignan los valores de Nombre, Descripcion y Precio del producto a los elementos de la vista
        binding.nombre.text = ofertasModel.Nombre
        binding.descripcion.text = ofertasModel.Descripcion
        binding.precio.text = ofertasModel.Precio

        // Usar Glide para cargar la imagen
        Glide.with(binding.imagencard.context)
            .load(ofertasModel.Imagen)
            .into(binding.imagencard)
    }
}
