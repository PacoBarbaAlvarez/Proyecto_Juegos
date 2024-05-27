package com.example.proyecto_juegos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_juegos.databinding.ActivityMenulateralBinding

class MenuLateral : AppCompatActivity() {

    private lateinit var binding: ActivityMenulateralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenulateralBinding.inflate(layoutInflater)
    }
}
