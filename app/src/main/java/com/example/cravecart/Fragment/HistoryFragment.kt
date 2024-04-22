package com.example.cravecart.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cravecart.Model.OrderDetails
import com.example.cravecart.R
import com.example.cravecart.adapter.Buyagainadapter
import com.example.cravecart.databinding.FragmentHistoryBinding
import com.example.cravecart.recentBuyItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {
   private lateinit var binding: FragmentHistoryBinding
   private lateinit var buyagainadapter: Buyagainadapter
   private lateinit var database: FirebaseDatabase
   private lateinit var auth: FirebaseAuth
   private lateinit var  userId:String
   private var listOfOrderItems:MutableList<OrderDetails> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHistoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
       //initialization
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        //retrive and display order history
        retriveBuyHistory()
        binding.BuyAgainFoodItem.setOnClickListener {
            seeItemRecentBuy()
        }
        return binding.root
    }

    private fun seeItemRecentBuy() {
        listOfOrderItems.firstOrNull()?.let { recentBuy->
            val intent=Intent(requireContext(),recentBuyItems::class.java)
            intent.putExtra("RecentBuyOrderItems",recentBuy)
            startActivity(intent)
        }
    }

    private fun retriveBuyHistory() {
        binding.BuyAgainFoodItem.visibility=View.INVISIBLE
        userId=auth.currentUser?.uid?:""

        val buyItemReference:DatabaseReference=database.reference.child("user").child(userId).child("BuyHistory")
        val sortingQuery=buyItemReference.orderByChild("currentTime")

        sortingQuery.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(buySnapShot in snapshot.children){
                    val buyHistoryItem=buySnapShot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let { listOfOrderItems.add(it) }
                }
                listOfOrderItems.reverse()
                if(listOfOrderItems.isNotEmpty()){
                    setDataInRecentBuyItem()
                    setPreviousBuyItemsRecyclerView()
                }
            }



            override fun onCancelled(error: DatabaseError) {
            }
        })}
    private fun setDataInRecentBuyItem() {
        binding.BuyAgainFoodItem.visibility=View.VISIBLE
        val recentOrderItem=listOfOrderItems.firstOrNull()
        recentOrderItem?.let {
            with(binding){
                BuyAgainFoodname.text=it.foodNames?.firstOrNull()?:""
                BuyAgainFoodPrice.text=it.foodPrices?.firstOrNull()?:""
                val image=it.foodImages?.firstOrNull()?:""
                val uri= Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(BuyAgainFoodIMage)
                listOfOrderItems.reverse()
                if (listOfOrderItems.isNotEmpty()){


                }
            }
        }
    }


    private fun setPreviousBuyItemsRecyclerView() {
            val buyAgainFoodname= mutableListOf<String>()
            val buyAgainFoodprice= mutableListOf<String>()
            val buyAgainFoodimage=  mutableListOf<String>()
            for (i in 1 until listOfOrderItems.size){
                listOfOrderItems[i].foodNames?.firstOrNull()?.let { buyAgainFoodname.add(it) }
                listOfOrderItems[i].foodPrices?.firstOrNull()?.let { buyAgainFoodprice.add(it) }
                listOfOrderItems[i].foodImages?.firstOrNull()?.let { buyAgainFoodimage.add(it) }
            }

        val rv=binding.BuyAgainRecyclerView
        rv.layoutManager=LinearLayoutManager(requireContext())
        buyagainadapter=Buyagainadapter(buyAgainFoodname , buyAgainFoodprice , buyAgainFoodimage ,requireContext())
        rv.adapter=buyagainadapter
    }





}