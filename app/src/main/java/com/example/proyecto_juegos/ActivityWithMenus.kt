package com.example.proyecto_juegos

import android.content.DialogInterface
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.DrawerLayoutUtils
import com.google.android.material.navigation.NavigationView

// Clase base para actividades con menús
abstract class ActivityWithMenus: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


    // Variable estática para rastrear la actividad actual
    companion object {
        var actividadActual = 0;
    }

    // Método para inflar el menú en la barra de acciones
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Habilitar o deshabilitar elementos del menú según la actividad actual
        for (i in 0 until menu.size()) {
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true
        }
        return true
    }

    // Método para manejar las acciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mostrar -> {
                actividadActual = 0;
                val intent = Intent(this, Listado::class.java)
                startActivity(intent)
                true
            }

            R.id.añadir -> {
                actividadActual = 1;
                val intent = Intent(this, Anadir::class.java)
                startActivity(intent)
                true
            }
            R.id.eliminar -> {
                actividadActual = 3;

                val intent = Intent(this, Eliminar::class.java)
                startActivity(intent)
                true
            }

            R.id.modificar -> {
                actividadActual = 2;
                val intent = Intent(this, Modificar::class.java)
                startActivity(intent)
                true
            }

            R.id.salir -> {
                finishAffinity()
                true
            }

            R.id.cerrar -> {
                mostrarDialogoCerrarSesion()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Método para mostrar un diálogo de confirmación para cerrar sesión
    private fun mostrarDialogoCerrarSesion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar sesión")
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
        builder.setPositiveButton("Sí") { dialogInterface: DialogInterface, _: Int ->
            // Acciones para cerrar sesión, como iniciar la actividad de inicio de sesión
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
