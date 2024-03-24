package com.example.cravecart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cravecart.databinding.ActivityDetails2Binding

class DetailsActivity2 : AppCompatActivity() {
    private lateinit var binding:ActivityDetails2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetails2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodName=intent.getStringExtra("MenuItemName")
        val foodImage=intent.getIntExtra("MenuItemImage",0)
        binding.DetailFoodname.text=foodName
        binding.DetailImage.setImageResource(foodImage)
        binding.DetailBackButton.setOnClickListener { finish() }
    }
}