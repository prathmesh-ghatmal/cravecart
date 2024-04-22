package com.example.cravecart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.Model.OrderDetails
import com.example.cravecart.adapter.RecentBuyAdapter
import com.example.cravecart.databinding.ActivityRecentBuyItemsBinding

class recentBuyItems : AppCompatActivity() {
    private val binding:ActivityRecentBuyItemsBinding by lazy {
        ActivityRecentBuyItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.RecentBuyBackButton.setOnClickListener { finish() }
        val recentOrderItems=intent.getSerializableExtra("RecentBuyOrderItems") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if (orderDetails.isNotEmpty()){
                val recentOrderItem=orderDetails[0]
                allFoodNames=recentOrderItem.foodNames as ArrayList<String>
                allFoodPrices=recentOrderItem.foodPrices as ArrayList<String>
                allFoodImages=recentOrderItem.foodImages as ArrayList<String>
                allFoodQuantities=recentOrderItem.foodQuantities as ArrayList<Int>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val rv=binding.RecentBuyRecyclerView
        rv.layoutManager=LinearLayoutManager(this)
        val adapter=RecentBuyAdapter(this,allFoodNames,allFoodImages,allFoodPrices,allFoodQuantities)
        rv.adapter=adapter
    }
}