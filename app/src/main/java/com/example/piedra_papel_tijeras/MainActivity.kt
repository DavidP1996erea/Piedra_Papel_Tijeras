package com.example.piedra_papel_tijeras

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


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

        if(imagenPapel.isClickable){

        var imagenes = findViewById<ImageView>(R.id.tijerasMaquina)
        imagenes.setImageResource(listaFotosJuego[(0..2).random()])

            imagenes.visibility = View.VISIBLE


            declararGanador(imagenPapel, imagenes)


        }else if(imagenTijeras.isClickable){
            var imagenes = findViewById<ImageView>(R.id.tijerasMaquina)
            imagenes.setImageResource(listaFotosJuego[(0..2).random()])

            imagenes.visibility = View.VISIBLE


            declararGanador(imagenTijeras, imagenes)



        }else{

            var imagenes = findViewById<ImageView>(R.id.tijerasMaquina)
            imagenes.setImageResource(listaFotosJuego[(0..2).random()])

            imagenes.visibility = View.VISIBLE


            declararGanador(imagenPiedra, imagenes)

        }

    }

    override fun declararGanador(imagenJugador: ImageView, imagenMaquina: ImageView) {

        val bitmapPapel = (findViewById<ImageView>(R.id.papel).getDrawable() as BitmapDrawable).bitmap
        val bitmapTijeras = (findViewById<ImageView>(R.id.tijeras).getDrawable() as BitmapDrawable).bitmap
        val bitmapPiedra = (findViewById<ImageView>(R.id.piedra).getDrawable() as BitmapDrawable).bitmap




        val bitmapJugador = (imagenJugador.getDrawable() as BitmapDrawable).bitmap
        val bitmap2Maquina = (imagenMaquina.getDrawable() as BitmapDrawable).bitmap



        if(bitmapJugador==bitmapPapel && bitmap2Maquina==bitmapPiedra){

            mensajeVictoria().show()

        }
        if(bitmapJugador==bitmapTijeras && bitmap2Maquina==bitmapPapel){

            mensajeVictoria().show()

        }

        if(bitmapJugador==bitmapPiedra && bitmap2Maquina==bitmapTijeras){
            mensajeVictoria().show()
        }



    }

    override fun mostrarResultadoTotal() {

    }


    fun mensajeVictoria():AlertDialog{

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Victoria!!!!")
        builder.setMessage("Has ganado a la máqina")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }

    fun mensajeEmpate():AlertDialog{

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Empate 😒👌!")
        builder.setMessage("Has quedado empate, al menos no has perdido")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }
}