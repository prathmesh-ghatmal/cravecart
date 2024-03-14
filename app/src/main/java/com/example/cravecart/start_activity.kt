package com.example.cravecart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cravecart.databinding.ActivityStartBinding

class start_activity : AppCompatActivity() {
    private val binding:ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener{
            val intent=Intent(this,login_activity::class.java)
            startActivity(intent)
        }
    }
}