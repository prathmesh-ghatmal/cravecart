package com.example.cravecart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cravecart.databinding.BuyAgainItemBinding

class Buyagainadapter(
    private val buyagainFoodname: ArrayList<String>,
    private val buyagainFoodprice: ArrayList<String>,
    private val buyagainFoodimage: ArrayList<Int>
): RecyclerView.Adapter<Buyagainadapter.BuyagainViewHolder>() {
    override fun onBindViewHolder(holder: BuyagainViewHolder, position: Int) {


        holder.bind(buyagainFoodname[position],buyagainFoodprice[position],buyagainFoodimage[position])    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyagainViewHolder {
        val binding=BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyagainViewHolder(binding)
    }

    override fun getItemCount(): Int =buyagainFoodname.size
    class BuyagainViewHolder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(foodname: String, foodprice: String, foodimage: Int) {
            binding.BuyAgainFoodname.text=foodname
            binding.BuyAgainFoodPrice.text=foodprice
            binding.BuyAgainFoodIMage.setImageResource(foodimage)
        }

    }


}