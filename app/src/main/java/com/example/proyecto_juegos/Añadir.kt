package com.example.proyecto_juegos

import android.os.Bundle
import android.os.PersistableBundle
import com.example.proyecto_juegos.databinding.ActivityAnadirBinding
import com.google.firebase.firestore.FirebaseFirestore

class Añadir : ActivityWithMenus() {

    private lateinit var binding: ActivityAnadirBinding
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        binding = ActivityAnadirBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}