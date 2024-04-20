package com.example.cravecart

import android.app.Activity
import android.content.ContentProviderClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cravecart.databinding.ActivityLoginBinding
import com.example.cravecart.databinding.ActivityStartBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class login_activity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
 private val binding:ActivityLoginBinding by lazy {
     ActivityLoginBinding.inflate(layoutInflater)
 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //initialization of firebase Auth

        auth = Firebase.auth

        //initialization of firebase Database

        database = com.google.firebase.Firebase.database.reference

        //initialization of Google signin
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.LoginButton.setOnClickListener {
            // getting text from edittexts
            email = binding.LoginEmail.text.toString().trim()
            password = binding.LoginPassword.text.toString().trim()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                loginuser(email, password)
            }

        }
        binding.LoginGoogleButton.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }


        binding.Donthavebutton.setOnClickListener{
            val intent=Intent(this,Signin_Activity::class.java)
            startActivity(intent)
        }
        binding.orbutton.setOnClickListener{
            val intent=Intent(this,Chooselocation::class.java)
            startActivity(intent)
        }

    }
    private fun loginuser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                val user = auth.currentUser
                UpdateUi(user)
            } else { // If sign in fails, display a message to the user.
                Log.w("wtf", "signInWithEmail:failure", task.exception)

                val message = task.exception!!.message

                if (message!!.contains("INVALID_LOGIN_CREDENTIALS")) {
                    Toast.makeText(
                        baseContext,
                        "invalid email or password",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    if (message!!.contains("We have blocked all requests from this device due to unusual activity")) {
                        Toast.makeText(
                            baseContext,
                            "Blocked due to too many failed attempts",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        if (message!!.contains("The email address is badly formatted")) {
                            Toast.makeText(
                                baseContext,
                                "Enter valid email",
                                Toast.LENGTH_SHORT,
                            ).show()
                        } else {
                            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }


    // launcher for google signin
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            Toast.makeText(this, "Successfully logged in", Toast.LENGTH_LONG).show()
                            UpdateUi(authTask.result?.user)
                            finish()
                        } else {
                            Log.e("GoogleSignIn", "signInWithCredential:failure", authTask.exception)
                            Toast.makeText(this, "Log in failed", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Log.e("GoogleSignIn", "getSignedInAccountFromIntent:failure", task.exception)
                    Toast.makeText(this, "Log in failed", Toast.LENGTH_LONG).show()
                }
            } else {
                Log.d("hii",result.resultCode.toString())
            }
        }

    override fun onStart() {
        super.onStart()
        val currentuser=auth.currentUser
        if(currentuser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    private fun UpdateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}