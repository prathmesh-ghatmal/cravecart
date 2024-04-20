package com.example.cravecart

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.cravecart.databinding.ActivityDetails2Binding

class DetailsActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityDetails2Binding
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetails2Binding.inflate(layoutInflater)
        setContentView(binding.root)
       foodDescription=intent.getStringExtra("MenuItemDescription")
       foodIngredients=intent.getStringExtra("MenuItemIngredients")
       foodPrice=intent.getStringExtra("MenuItemPrice")
       foodName=intent.getStringExtra("MenuItemName")
       foodImage=intent.getStringExtra("MenuItemImage")

        with(binding){
            DetailFoodname.text=foodName
            DetailDescription.text=foodDescription
            DetailIngredients.text=foodIngredients
            Glide.with(this@DetailsActivity2).load(Uri.parse(foodImage)).into(DetailImage)
        }

        binding.DetailBackButton.setOnClickListener { finish() }
    }
}