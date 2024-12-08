package com.smartdevices.petpal.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.smartdevices.petpal.MainActivity
import com.petpal.R
import com.smartdevices.petpal.tools.PreferenceManager
import com.smartdevices.petpal.ui.LoginScreen
import com.smartdevices.petpal.ui.theme.JetpackComposeTestTheme

class LoginActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private val preferenceManager : PreferenceManager by lazy { PreferenceManager(applicationContext) }

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //preferenceManager.setSaveMethod("NOT SET")

        Log.d(TAG, preferenceManager.getSaveMethod())
        if (preferenceManager.getSaveMethod() != "NOT SET") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close LoginActivity
        }

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id)) // from google-services.json
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Register for the activity result callback to handle the sign-in intent
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val signInAccount = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(signInAccount)
            }else{
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }

        setContent {
            JetpackComposeTestTheme(preferenceManager) {
                LoginScreen(preferenceManager = PreferenceManager(applicationContext),
                    onGoogleSignInClick = { signInWithGoogle() },
                    onLocalSignInClick = { localSignIn() }
                )
            }
        }
    }

    private fun signInWithGoogle() {
        // Start the Google sign-in intent
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.result
            if (account != null) {
                firebaseAuthWithGoogle(account)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Google sign-in failed", e)
            Toast.makeText(this, "Google sign-in failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle: ${account.id}")
        // Get Firebase credentials
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        Log.d(TAG, "User id: ${user.uid}")
                        preferenceManager.setUUID(user.uid)
                        user.let {
                            preferenceManager.setUserName(it.displayName ?: "")
                            preferenceManager.setUserEmail(it.email ?: "")
                            preferenceManager.setUserPhoto(it.photoUrl.toString())
                        }
                        Log.d(TAG, "User name: ${preferenceManager.getUserName()}")
                        Log.d(TAG, "User email: ${preferenceManager.getUserEmail()}")
                        Log.d(TAG, "User photo: ${preferenceManager.getUserPhoto()}")
                    }
                    updateUI(user)
                } else {
                    // If sign-in fails, display a message
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun localSignIn() {
        preferenceManager.setSaveMethod("local", null)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close LoginActivity
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            preferenceManager.setSaveMethod("cloud", null)
            // Successfully signed in, navigate to the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close LoginActivity
        } else {
            Toast.makeText(this, "Login failed. Try again.", Toast.LENGTH_SHORT).show()
        }
    }
}