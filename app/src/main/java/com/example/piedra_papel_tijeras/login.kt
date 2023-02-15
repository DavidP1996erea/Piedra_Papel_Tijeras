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

    private lateinit var auth: FirebaseAuth
    var fireStoreDatabase = FirebaseFirestore.getInstance()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
            signIn()
        }

    }



    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            val intent : Intent = Intent(this , MainActivity::class.java)
            intent.putExtra("name" , user?.displayName)
            intent.putExtra("correo", user?.email)
            startActivity(intent)


                // create a dummy data
                val hashMap = hashMapOf<String, String>(
                    "Correo" to user?.email.toString(),
                    "Nombre" to user?.displayName.toString(),
                    )

                // use the add() method to create a document inside users collection
                fireStoreDatabase.collection("Usuarios")
                    .add(hashMap)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "Added document with ID ${it.id}")
                    }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error adding document $exception")
                    }

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private fun signIn(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

// Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

}