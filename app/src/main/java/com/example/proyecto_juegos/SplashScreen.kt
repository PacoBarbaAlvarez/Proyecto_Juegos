package com.example.proyecto_juegos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.proyecto_juegos.databinding.ActivitySplashBinding

class SplashScreen : AppCompatActivity(){

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash=installSplashScreen()
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
        screenSplash.setKeepOnScreenCondition{true}
        Thread.sleep(200)
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}