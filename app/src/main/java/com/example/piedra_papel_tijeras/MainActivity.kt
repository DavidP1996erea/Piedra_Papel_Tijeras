package com.example.piedra_papel_tijeras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() , comunicador {


    var listaFotosJuego = arrayListOf(
        R.drawable.tijeras, R.drawable.papel_higienico, R.drawable.piedra
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

    }



    override fun rondaDeJuego() {



        var imagenPapel = findViewById<ImageView>(R.id.papel)
        var imagenTijeras = findViewById<ImageView>(R.id.tijeras)
        var imagenPiedra = findViewById<ImageView>(R.id.piedra)

        if(imagenTijeras.isClickable || imagenPapel.isClickable || imagenPiedra.isClickable){



        var imagenes = findViewById<ImageView>(R.id.tijerasMaquina)
        imagenes.setImageResource(listaFotosJuego[(0..2).random()])

            imagenes.visibility = View.VISIBLE
        }

    }

    override fun declararGanador(imagenJugador: ImageView, imagenMaquina: ImageView) {




    }

    override fun mostrarResultadoTotal() {

    }
}