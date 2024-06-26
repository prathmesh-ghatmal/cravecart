package com.example.cravecart.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cravecart.databinding.BuyAgainItemBinding

class Buyagainadapter(
    private val buyagainFoodname: MutableList<String>,
    private val buyagainFoodprice: MutableList<String>,
    private val buyagainFoodimage: MutableList<String>,
    private var requireContext:Context
): RecyclerView.Adapter<Buyagainadapter.BuyagainViewHolder>() {
    override fun onBindViewHolder(holder: BuyagainViewHolder, position: Int) {


        holder.bind(buyagainFoodname[position],buyagainFoodprice[position],buyagainFoodimage[position])    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyagainViewHolder {
        val binding=BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyagainViewHolder(binding)
    }

    override fun getItemCount(): Int =buyagainFoodname.size
  inner  class BuyagainViewHolder(private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(foodname: String, foodprice: String, foodimage: String) {
            binding.BuyAgainFoodname.text=foodname
            binding.BuyAgainFoodPrice.text=foodprice
            val uriString=foodimage
            val uri=Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.BuyAgainFoodIMage)
        }

    }


}