package com.example.proyecto_juegos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto_juegos.databinding.ActivityListadoBinding

class Listado : ActivityWithMenus() {
    lateinit var binding: ActivityListadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}