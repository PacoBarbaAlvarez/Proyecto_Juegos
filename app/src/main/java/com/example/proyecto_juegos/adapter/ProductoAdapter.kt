package com.example.proyecto_juegos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_juegos.Productos
import com.example.proyecto_juegos.R

// Clase que actúa como adaptador para el RecyclerView
class ProductoAdapter (var ProductoList: List<Productos>): RecyclerView.Adapter<ProductoViewHolder>(){

    // Método llamado cuando el adaptador necesita crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        // Inflar el diseño de elemento de la lista y devolver un ViewHolder que contenga la vista
        val layoutInflater= LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.item_lista,parent,false))
    }

    // Método llamado cuando el adaptador necesita actualizar la información de un ViewHolder
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        // Obtener el objeto Producto en la posición dada y pasarlos al ViewHolder para que los muestre
        val item= ProductoList[position]
        holder.render(item)
    }

    // Método que devuelve el número total de elementos en el conjunto de datos
    override fun getItemCount(): Int {
        return ProductoList.size
    }

    // Método para actualizar la lista de productos y notificar al adaptador que los datos han cambiado
    fun actualizarOfertas(ListaProductos: List<Productos>){
        this.ProductoList=ListaProductos
        notifyDataSetChanged()
    }
}
