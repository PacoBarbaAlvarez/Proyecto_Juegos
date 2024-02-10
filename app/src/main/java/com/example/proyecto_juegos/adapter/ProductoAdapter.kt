package com.example.proyecto_juegos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_juegos.Productos
import com.example.proyecto_juegos.R

class ProductoAdapter (var ProductoList: List<Productos>): RecyclerView.Adapter<ProductoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.item_lista,parent,false))
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item= ProductoList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return ProductoList.size
    }
    fun actualizarOfertas(ListaProductos: List<Productos>){
        this.ProductoList=ListaProductos
        notifyDataSetChanged()
    }
}