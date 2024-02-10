package com.example.proyecto_juegos.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_juegos.Productos
import com.example.proyecto_juegos.databinding.ItemListaBinding

class ProductoViewHolder (view: View): RecyclerView.ViewHolder(view){
    private val binding= ItemListaBinding.bind(view)
    fun render(ofertasModel:Productos)
    {
        binding.nombre.text = ofertasModel.Nombre
        binding.descripcion.text = ofertasModel.Descripcion
        binding.precio.text = ofertasModel.Precio
    }

}