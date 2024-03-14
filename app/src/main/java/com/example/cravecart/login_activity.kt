package com.example.cravecart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cravecart.databinding.ActivityLoginBinding
import com.example.cravecart.databinding.ActivityStartBinding

class login_activity : AppCompatActivity() {
 private val binding:ActivityLoginBinding by lazy {
     ActivityLoginBinding.inflate(layoutInflater)
 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
       binding.loginbutton1.setOnClickListener{
           val intent=Intent(this,Signin_Activity::class.java)
           startActivity(intent)
       }

        binding.Donthavebutton.setOnClickListener{
            val intent=Intent(this,Signin_Activity::class.java)
            startActivity(intent)
        }

    }
}