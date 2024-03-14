package com.example.cravecart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cravecart.databinding.ActivitySigninBinding

class Signin_Activity : AppCompatActivity() {
    private val binding:ActivitySigninBinding by lazy{
       ActivitySigninBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.alreadyhaveac.setOnClickListener{
          val  intent=Intent(this,login_activity::class.java)
            startActivity(intent)
        }
    }
}