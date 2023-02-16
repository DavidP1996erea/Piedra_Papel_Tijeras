package com.example.piedra_papel_tijeras

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


class login : AppCompatActivity() {

    // Se crean las variables
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStoreDatabase : FirebaseFirestore


    // Utilizado en el método signIn
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        // Introduce como parámetro en este método la cuenta introducida
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /* Se inicializa las variables con las instance de
            la autenticación y de la base de datos
        */
        auth = FirebaseAuth.getInstance()
        fireStoreDatabase = FirebaseFirestore.getInstance()

        // Se llama al método signIn al pulsar el botón
        findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
            signIn()
        }

    }


    /**
     * Se comprueba que la autenticación se haya realizado, y se guarda en una variable
     * el usuario que está actualmente iniciado. Luego se envía al a ativity main
     * junto al nombre del usuario, que se usará para mostrarlo. Por último
     * se guarda en la base de datos el correo y el nombre del usuario, creando
     * un hasMap con ambos datos, y añadiendo este a la colección Usuarios
     * de la base de datos.
     */
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            val intent = Intent(this , MainActivity::class.java)
            intent.putExtra("name" , user?.displayName)

            startActivity(intent)


                // Se crea el hasMap con los datos del usuario
                val hashMap = hashMapOf<String, String>(
                    "Correo" to user?.email.toString(),
                    "Nombre" to user?.displayName.toString(),
                    )

                // Se añade los datos del usuario al collection Usuarios
                fireStoreDatabase.collection("Usuarios")
                    .add(hashMap)

        }
    }

    /**
     * Método que primero crea un array con los servicios usados por el
     * AuthUI, en nuestro caso la autenticación por google. Luego
     * se crea y se inicia el intento de conectar con el correo.
     */
    private fun signIn(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )



        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

}