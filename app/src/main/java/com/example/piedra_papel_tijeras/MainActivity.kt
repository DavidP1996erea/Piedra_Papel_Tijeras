package com.example.piedra_papel_tijeras

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), comunicador {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


    }





    fun crearImagenRandom(): ImageView {

        var listaFotosJuego = arrayOf(
            findViewById<ImageView>(R.id.tijerasMaquina), findViewById(R.id.papelMaquina),
            findViewById(R.id.piedraMaquina)
        )

        for(fotos:ImageView in listaFotosJuego){

            fotos.visibility = View.INVISIBLE

        }

        return listaFotosJuego[(0..2).random()]

    }

    override fun jugarPapel() {
        var imagenPapel = findViewById<ImageView>(R.id.papelJugador)

        var imagenMaquina = crearImagenRandom()
        imagenMaquina.visibility = View.VISIBLE

        declararGanador(imagenPapel, imagenMaquina)

    }

    override fun jugarPiedra() {
        var imagenPiedra = findViewById<ImageView>(R.id.piedraJugador)

        var imagenMaquina = crearImagenRandom()
        imagenMaquina.visibility = View.VISIBLE

        declararGanador(imagenPiedra, imagenMaquina)

    }

    override fun jugarTijeras() {
        var imagenTijera = findViewById<ImageView>(R.id.tijerasJugador)

        var imagenMaquina = crearImagenRandom()
        imagenMaquina.visibility = View.VISIBLE

        declararGanador(imagenTijera, imagenMaquina)
    }


    override fun declararGanador(imagenJugador: ImageView, imagenMaquina: ImageView) {

        val bitmapPapel = (findViewById<ImageView>(R.id.papelJugador).getDrawable() as BitmapDrawable).bitmap
        val bitmapTijeras = (findViewById<ImageView>(R.id.tijerasJugador).getDrawable() as BitmapDrawable).bitmap
        val bitmapPiedra = (findViewById<ImageView>(R.id.piedraJugador).getDrawable() as BitmapDrawable).bitmap

        val bitmapPapelMaquina = (findViewById<ImageView>(R.id.papelMaquina).getDrawable() as BitmapDrawable).bitmap
        val bitmapTijerasMaquina = (findViewById<ImageView>(R.id.tijerasMaquina).getDrawable() as BitmapDrawable).bitmap
        val bitmapPiedraMaquina = (findViewById<ImageView>(R.id.piedraMaquina).getDrawable() as BitmapDrawable).bitmap




        val bitmapJugador = (imagenJugador.getDrawable() as BitmapDrawable).bitmap
        val bitmap2Maquina = (imagenMaquina.getDrawable() as BitmapDrawable).bitmap



        if(bitmapJugador==bitmapPapel && bitmap2Maquina==bitmapPiedraMaquina){

            mensajeVictoria().show()

        }
        if(bitmapJugador==bitmapTijeras && bitmap2Maquina==bitmapPapelMaquina){

            mensajeVictoria().show()

        }

        if(bitmapJugador==bitmapPiedra && bitmap2Maquina==bitmapTijerasMaquina){
            mensajeVictoria().show()
        }

    }

    override fun mostrarResultadoTotal() {

    }


    fun mensajeVictoria(): AlertDialog {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Victoria!!!!")
        builder.setMessage("Has ganado a la máqina")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }

    fun mensajeEmpate(): AlertDialog {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Empate 😒👌!")
        builder.setMessage("Has quedado empate, al menos no has perdido")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }
}