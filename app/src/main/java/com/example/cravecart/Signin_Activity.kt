package com.example.cravecart

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cravecart.Model.UserModel
import com.example.cravecart.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Signin_Activity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding: ActivitySigninBinding by lazy {
        ActivitySigninBinding.inflate(layoutInflater)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialization of firebase Auth

        auth = Firebase.auth

        //initialization of firebase Database

        database = Firebase.database.reference
        binding.CreateAccountButton.setOnClickListener {
            username = binding.UserName.text.toString().trim()
            email = binding.SignInEmail.text.toString().trim()
            password = binding.SignInPassword.text.toString().trim()
            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }

        }



        binding.alreadyhaveac.setOnClickListener {
            val intent = Intent(this, login_activity::class.java)
            startActivity(intent)
        }
    }
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Account Created Sucessfully",Toast.LENGTH_SHORT).show()
                SaveUserData()
                val intent = Intent(this, login_activity::class.java)
                startActivity(intent)

            }else{
                val message = task.exception!!.message

                if (message!!.contains("Password should be at least 6 characters")) {
                    Toast.makeText(
                        baseContext,
                        "Password should be at least 6 characters",
                        Toast.LENGTH_SHORT,
                    ).show()}else{
                    if (message!!.contains("The email address is already in use by another account")) {
                        Toast.makeText(
                            baseContext,
                            "Email already exists",
                            Toast.LENGTH_SHORT,
                        ).show()}else{
                        if (message!!.contains("The email address is badly formatted")) {
                            Toast.makeText(
                                baseContext,
                                "Enter valid email",
                                Toast.LENGTH_SHORT,
                            ).show()}else{ Toast.makeText(this,"account creation Failed",Toast.LENGTH_SHORT).show()}
                    }
                }

                Log.d("Account", "createAccount: Failure",task.exception)
            }
        }
    }
    //  saving data into the database
    private fun SaveUserData() {
        //get text from edittext
        username = binding.UserName.text.toString().trim()

        email = binding.SignInEmail.text.toString().trim()
        password = binding.SignInPassword.text.toString().trim()
        val user=UserModel(username,email,password)
        val userid=FirebaseAuth.getInstance().currentUser!!.uid
        //saving in firebase
        database.child("user").child(userid).setValue(user)
    }
}