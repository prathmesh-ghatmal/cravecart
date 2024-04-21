package com.example.cravecart

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cravecart.Model.CartItem
import com.example.cravecart.databinding.ActivityDetails2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DetailsActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityDetails2Binding
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetails2Binding.inflate(layoutInflater)

        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
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
        binding.AddToCartButton.setOnClickListener { addItemToCart() }
    }

    private fun addItemToCart() {

        val database=FirebaseDatabase.getInstance().reference
        val userId= auth.currentUser?.uid?:""

        //Creating cart item object
        val cartItem=CartItem(foodName.toString(),foodPrice.toString(),foodImage.toString(),foodDescription.toString(),1)

        //save data in the database
        database.child("user").child(userId).child("cartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"item added sucessfully",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"item not added ",Toast.LENGTH_SHORT).show()
        }
    }
}