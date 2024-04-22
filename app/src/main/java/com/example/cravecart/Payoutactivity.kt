package com.example.cravecart


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cravecart.Fragment.CartFragment

import com.example.cravecart.Fragment.CongratsBottomSheetFragment
import com.example.cravecart.Model.OrderDetails
import com.example.cravecart.databinding.ActivityPayoutactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Payoutactivity : AppCompatActivity() {
    lateinit var binding: ActivityPayoutactivityBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalamount: String
    private var foodItemName: ArrayList<String> = arrayListOf()
    private var foodItemPrice: ArrayList<String> = arrayListOf()
    private var foodItemImage: ArrayList<String> = arrayListOf()
    private var foosItemDescription: ArrayList<String> = arrayListOf()
    private var foodItemQuantities: ArrayList<Int> = arrayListOf()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayoutactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize firebase and user details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()
        //set user data
        setUserData()

        val intent = intent
        if (intent != null && intent.hasExtra("FoodNames")) {
            val foodNames = intent.getStringArrayListExtra("FoodNames")
            val foodPrices = intent.getStringArrayListExtra("FoodPrice")
            val foodDescriptions = intent.getStringArrayListExtra("FoodDescription")
            val foodImages = intent.getStringArrayListExtra("FoodImage")
            val foodQuantity = intent.getIntegerArrayListExtra("FoodItemQuantities")
            if (foodNames != null) {
                // The "FoodNames" extra is not null
                // Proceed with using the value
                foodItemName = foodNames
                foodItemPrice = foodPrices as ArrayList<String>
                foosItemDescription = foodDescriptions as ArrayList<String>
                foodItemImage = foodImages as ArrayList<String>
                foodItemQuantities = foodQuantity as ArrayList<Int>
            } else {
                // The "FoodNames" extra is null
                // Handle this case accordingly
                Log.e("PayoutActivity", "FoodNames extra is null")
            }
        } else {
            // The intent is null or "FoodNames" extra is missing
            // Handle this case accordingly
            Log.e("PayoutActivity", "Intent is null or FoodNames extra is missing")
        }

        // if(foodItemQuantities !=null){ for (name in foodItemQuantities){Log.d("bappa","notworks${name}")}}else{Log.d("bappa","notworks")}
        totalamount = totalAmount().toString()
        //   var i= checki()

        binding.PayoutTotal.setText(totalamount)
        binding.PlaceMyOrder.setOnClickListener {
            //get data from textview
            name = binding.PayoutName.text.toString().trim()
            phone = binding.PayoutPhone.text.toString().trim()
            address = binding.PayoutAddress.text.toString().trim()
            if (name.isBlank() || phone.isBlank() || address.isBlank()) {
                Toast.makeText(this, "please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }

        }
        binding.PlaceOrderBackButton.setOnClickListener {
            finish()
        }
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("orderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            foodItemName,
            foodItemPrice,
            foodItemImage,
            foodItemQuantities,
            address,
            totalamount,
            phone,
            time,
            itemPushKey,
            false,
            false
        )
        val orderReference = databaseReference.child("orderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomsheetDialog = CongratsBottomSheetFragment()
            bottomsheetDialog.show(supportFragmentManager, "Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }.addOnFailureListener { Toast.makeText(this, "Order Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
       databaseReference.child("user").child(userId).child("BuyHistory")
           .child(orderDetails.itemPushKey!!)
           .setValue(orderDetails).addOnSuccessListener {  }
    }

    private fun removeItemFromCart() {
    val cartItemReference=databaseReference.child("user").child(userId).child("cartItems")
        cartItemReference.removeValue()
    }

    private fun totalAmount(): Int {
        var total = 0
        for (i in 0 until foodItemPrice.size) {
            var quantity = foodItemQuantities[i]
            total += foodItemPrice[i].toInt() * quantity
        }
        return total
    }

    //this is real hardwork
    /*  private fun checki() :Int{
          val intent = intent
          if (intent != null && intent.hasExtra("FoodItemQuantities")&& intent.hasExtra("FoodImage")&& intent.hasExtra("FoodDescription")) {
              val foodNames = intent.getStringArrayListExtra("FoodPrice")
              foodItemImage=intent.getStringArrayListExtra("FoodImage") as ArrayList<String>
              val quantity = intent.getIntegerArrayListExtra("FoodItemQuantities")
              if (foodNames != null) {
                  var total=0
                  var b=0
                  var i=0
                  Log.d("PayoutActivity", "this is dj pg: ${foodNames[1]}")
                  for (name in foodNames) {
                     b =name.toInt()
                      total+=b* quantity!![i]
                      Log.d("PayoutActivity", "FoodName: ${name}")
                      i++
                  }
                  return total
                 // Log.d("PayoutActivity", "FoodName: ${b}")
              } else {
                  Log.e("PayoutActivity", "FoodNames extra is null")
                  // Handle the case when FoodNames extra is null
              }
          } else {
              Log.e("PayoutActivity", "Intent is null or FoodNames extra is missing")
              // Handle the case when intent is null or FoodNames extra is missing
          }
   return 0
      }*/


    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userReference: DatabaseReference = databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val names = snapshot.child("name").getValue(String::class.java) ?: ""
                        val addresses = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phonnes = snapshot.child("phone").getValue(String::class.java) ?: ""
                        binding.apply {
                            PayoutName.setText(names)
                            PayoutAddress.setText(addresses)
                            PayoutPhone.setText(phonnes)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}