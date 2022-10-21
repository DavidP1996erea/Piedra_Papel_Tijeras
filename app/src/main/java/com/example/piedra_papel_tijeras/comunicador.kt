package com.example.piedra_papel_tijeras

import android.widget.ImageView

interface comunicador {
   fun jugarPapel()
   fun jugarPiedra()
   fun jugarTijeras()
   fun declararGanador(imagenJugador: ImageView,imagenMaquina: ImageView)
   fun mostrarResultadoTotal()

}