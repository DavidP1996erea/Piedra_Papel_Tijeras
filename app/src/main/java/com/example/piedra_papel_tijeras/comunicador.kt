package com.example.piedra_papel_tijeras

import android.widget.ImageView

interface comunicador {
   fun rondaDeJuego()
   fun declararGanador(imagenJugador: ImageView,imagenMaquina: ImageView)
   fun mostrarResultadoTotal()

}