package com.example.cravecart.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.Model.CartItem
import com.example.cravecart.Payoutactivity
import com.example.cravecart.R
import com.example.cravecart.adapter.cartadapter
import com.example.cravecart.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartadapter: cartadapter
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retriveCartItems()

        binding.Proceedbutton.setOnClickListener {
            //get order details before proceeding to checkout
            getOrderItemsDetails()

        }
        return binding.root
    }

    private fun getOrderItemsDetails() {
        //database Reference
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val orderReference: DatabaseReference =
            database.reference.child("user").child(userId).child("cartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()


        //get items Quantities

        val foodQuantities = cartadapter.getUpdatedItemQuantities()

        orderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children) {
                    //get cartitems
                    val orderItems = foodSnapShot.getValue(CartItem::class.java)
                    //add item details in to list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }

                }
                orderNow(foodName, foodPrice, foodDescription, foodImage, foodQuantities)
            }

            private fun orderNow(
                foodName: MutableList<String>,
                foodPrice: MutableList<String>,
                foodDescription: MutableList<String>,
                foodImage: MutableList<String>,
                foodQuantities: MutableList<Int>
            ) {
                if (isAdded && context!=null){
                    val intent=Intent(requireContext(),Payoutactivity::class.java)
                    intent.putStringArrayListExtra("FoodNames",foodName as ArrayList<String>)
                    intent.putExtra("FoodPrice",foodPrice as ArrayList<String>)
                    intent.putExtra("FoodImage",foodImage as ArrayList<String>)
                    intent.putExtra("FoodDescription",foodDescription as ArrayList<String>)
                    intent.putExtra("FoodItemQuantities",foodQuantities as ArrayList<Int>)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "order failed please try again", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun retriveCartItems() {
        //database Reference
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodRef: DatabaseReference =
            database.reference.child("user").child(userId).child("cartItems")

        //lists to store cart items
        foodNames = mutableListOf()
        foodDescriptions = mutableListOf()
        foodImageUri = mutableListOf()
        foodIngredients = mutableListOf()
        foodPrices = mutableListOf()
        quantity = mutableListOf()

        //fetch data from database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children) {
                    //get the cartItems object from the child node
                    val cartItems = foodSnapShot.getValue(CartItem::class.java)
                    //add cart items Details to list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }

                    cartItems?.foodQuantity?.let { quantity.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartadapter = cartadapter(
                    requireContext(),
                    foodNames,
                    foodPrices,
                    foodImageUri,
                    foodDescriptions,
                    quantity
                )
                binding.cartrecyclerview.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartrecyclerview.adapter = cartadapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetched", Toast.LENGTH_SHORT).show()

            }
        })
    }

    companion object {

    }
}