package com.petpal.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.petpal.MainActivity
import com.petpal.R
import com.petpal.tools.PreferenceManager
import com.petpal.ui.LoginScreen

class LoginActivity : ComponentActivity() {

    private val preferenceManager: PreferenceManager by lazy { PreferenceManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user has logged in previously
        if (preferenceManager.getSaveMethod() != "NOT SET") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close LoginActivity
            return
        }

        setContent {
            LoginScreen(
                preferenceManager = preferenceManager,
                onGoogleSignInClick = { signInWithGoogle() },
                onLocalSignInClick = { localSignIn() }
            )
        }
    }

    private fun localSignIn() {
        preferenceManager.setSaveMethod("local")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close LoginActivity
    }

    private fun signInWithGoogle() {
        val googleSignInHelper = GoogleSignInHelper(this)
        googleSignInHelper.signIn()
    }
}
