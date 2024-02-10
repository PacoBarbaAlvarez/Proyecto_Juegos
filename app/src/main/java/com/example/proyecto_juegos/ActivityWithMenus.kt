package com.example.proyecto_juegos

import android.content.DialogInterface
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class ActivityWithMenus: AppCompatActivity() {

    companion object {
        var actividadActual = 0;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Relacionamos la clase con el layout del menú que hemos creado:
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        //Desactivar la opción de la actividad en la que ya estamos:
        for (i in 0 until menu.size()) {
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mostrar -> {
                actividadActual = 0;
                //Hacemos que se habra la pantalla del listado de productos
                val intent = Intent(this, Principal::class.java)
                startActivity(intent)
                true
            }

            R.id.añadir -> {
                actividadActual = 0;
                //Hacemos que se habra la pantalla del listado de productos
                val intent = Intent(this, Añadir::class.java)
                startActivity(intent)
                true
            }

            R.id.eliminar -> {
                actividadActual = 0;
                //Hacemos que se habra la pantalla del listado de productos
                val intent = Intent(this, Eliminar::class.java)
                startActivity(intent)
                true
            }

            R.id.modificar -> {
                actividadActual = 0;
                //Hacemos que se habra la pantalla del listado de productos
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

    private fun mostrarDialogoCerrarSesion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar sesión")
        builder.setMessage("¿Estás seguro de que deseas cerrar sesión?")
        builder.setPositiveButton("Sí") { dialogInterface: DialogInterface, _: Int ->
            // Realizar aquí las acciones para cerrar sesión, como iniciar la actividad de inicio de sesión
            // Por ejemplo:
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
