package com.example.piedra_papel_tijeras


import android.content.ContentValues.TAG
import android.graphics.drawable.BitmapDrawable

import android.os.Bundle
import android.util.Log

import android.view.View

import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(), comunicador {

    /**
     * Variables que servirán para llevar un recuento de victorias
     */
    var puntosJugador=0
    var puntosMaquina=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Se recoge el nombre del usuario mandado por el intent del login
        val displayName = intent.getStringExtra("name")
        // Se muestra el nombre
        var nombreCorreo = findViewById<TextView>(R.id.textView)
        nombreCorreo.text = displayName



    }



    /**
     * Este método cambia la imagen de referencia que toma piedraMaquina. Esto se hace a través
     * de dos checks, que al estar activados cambian la imagen de la piedra.
     */


    fun cambiarSkin(view: View){

        var laROCA = findViewById<CheckBox>(R.id.cambiarSkin)
        var laROCAHUEVO = findViewById<CheckBox>(R.id.cambiarSkin2)
        var imagenPiedra = findViewById<ImageView>(R.id.piedraMaquina)


        if(laROCA.isChecked){
            imagenPiedra.setImageResource(R.drawable.laroca)
        }else if(laROCAHUEVO.isChecked){
            imagenPiedra.setImageResource(R.drawable.laroca2)
        }else{
            imagenPiedra.setImageResource(R.drawable.piedra)
        }

    }

    /**
     * Se crea un array con las 3 fotos que se necesita de la parte de la máquina. Siempre
     * que entra en este método se recorre la lista y se ponen las imagenes en invisible,
     * para que no se pongan unas encima de otras. Este método retorna un objeto random de ese
     * array, de esta forma se consigue que cada vez que se llama a este método devuelva
     * una imagen diferente.
     *
     */

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


    /**
     * Los siguientes tres métodos son iguales, primero se crean dos variables tipo ImagenView,
     * en la primera se guarda la imagen correspondiente del jugador, en este caso papel, en el
     * siguiente método piedra y el último tijeras. En la segunda variable se llama al método
     * crearImagenRandom, que devuelve una de las  tres imágenes de forma random, esto se hace
     * en los tres métodos. Por último, tanto la imagen del jugador como la aleatoria de la máquina
     * se meten como parámetros en un método llamado declararGanador, que será ahí donde se
     * valide quién es el ganador de cada baza.
     */

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

    /**
     * En este método se hace una serie de if que validarán quién será el ganador de cada baza,
     * para ello se crean 6 variables, tres de ellas para los elementos del jugador y las otras
     * tres para los elementos de la máquina. Luego se crean 2 variables indicando quién es el
     * jugador y quién es la máquina. Todas las validaciones llamarán a otros dos métodos
     * que devuelven un mensaje indicando si el jugador a perdido o ganado.
     */

    override fun declararGanador(imagenJugador: ImageView, imagenMaquina: ImageView) {

        val bitmapPapel = (findViewById<ImageView>(R.id.papelJugador).drawable as BitmapDrawable).bitmap
        val bitmapTijeras = (findViewById<ImageView>(R.id.tijerasJugador).drawable as BitmapDrawable).bitmap
        val bitmapPiedra = (findViewById<ImageView>(R.id.piedraJugador).drawable as BitmapDrawable).bitmap

        val bitmapPapelMaquina = (findViewById<ImageView>(R.id.papelMaquina).drawable as BitmapDrawable).bitmap
        val bitmapTijerasMaquina = (findViewById<ImageView>(R.id.tijerasMaquina).drawable as BitmapDrawable).bitmap
        val bitmapPiedraMaquina = (findViewById<ImageView>(R.id.piedraMaquina).drawable as BitmapDrawable).bitmap




        val bitmapJugador = (imagenJugador.drawable as BitmapDrawable).bitmap
        val bitmap2Maquina = (imagenMaquina.drawable as BitmapDrawable).bitmap


        /**
         * Se valida en primer lugar el ganador y el perdedor cuando el jugador juega papel.
         * En estas validaciones se aumentarán las victorias de la máquina y del jugador.
         */
        if(bitmapJugador==bitmapPapel && bitmap2Maquina==bitmapPiedraMaquina){

            mensajeVictoria().show()
            puntosJugador++

        }else if(bitmapJugador==bitmapPapel && bitmap2Maquina==bitmapTijerasMaquina){

            mensajeDerrota().show()
            puntosMaquina++
        }


        /**
         * Luego se valida el resultado cuando el jugador escoje tijeras
         */
        if(bitmapJugador==bitmapTijeras && bitmap2Maquina==bitmapPapelMaquina){

            mensajeVictoria().show()
            puntosJugador++

        }else if (bitmapJugador==bitmapTijeras && bitmap2Maquina==bitmapPiedraMaquina){

            mensajeDerrota().show()
            puntosMaquina++

        }

        /**
         * Por último, cuando el jugador elige piedra
         */

        if(bitmapJugador==bitmapPiedra && bitmap2Maquina==bitmapTijerasMaquina){
            mensajeVictoria().show()
            puntosJugador++

        }else if (bitmapJugador==bitmapPiedra && bitmap2Maquina==bitmapPapelMaquina){
            mensajeDerrota().show()
            puntosMaquina++

        }

        mostrarResultadoTotal()

    }

    /**
     * En este método se modifica el texto del TextView de los puntos del jugador y de la
     * máquina, cambiando ambos por las variables creadas al inicio.
     */
    override fun mostrarResultadoTotal() {

        var puntuajeJugador = findViewById<TextView>(R.id.mostrarResultadoJugador)
        var puntuajeMaquina = findViewById<TextView>(R.id.mostrarResultadoMaquina)
        puntuajeJugador.text=puntosJugador.toString()
        puntuajeMaquina.text=puntosMaquina.toString()

    }

    override fun resetearPuntos() {
        var reset = findViewById<ImageView>(R.id.resetearPuntos)
        var puntosJugador = findViewById<TextView>(R.id.mostrarResultadoJugador)
        var puntosMaquina = findViewById<TextView>(R.id.mostrarResultadoMaquina)


        puntosJugador.text="0"
        puntosMaquina.text="0"
    }


    /**
     * Método que devuelve un mensaje de victoria
     */
    fun mensajeVictoria(): AlertDialog {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Victoria!!!!")
        builder.setMessage("Has ganado a la máqina")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }

    /**
     * Método que devuelve un mensaje de derrota
     */
    fun mensajeDerrota(): AlertDialog {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Has perdido...😦😵!")
        builder.setMessage("Has perdido ante la máquina, adiós")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }



}


