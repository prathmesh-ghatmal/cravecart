package com.example.cravecart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cravecart.DetailsActivity2
import com.example.cravecart.databinding.PopularItemBinding

class Popularadapter (private val items:List<String>,private val price:List<String>,private val image:List<Int>,private val requireContext:Context): RecyclerView.Adapter<Popularadapter.PopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
val item=items[position]
        val images=image[position]
        val prices=price[position]
        holder.bind(item,images,prices)

        holder.itemView.setOnClickListener {
            //set on click listner for details code
            val intent= Intent(requireContext, DetailsActivity2::class.java)
            intent.putExtra("MenuItemName",item)
            intent.putExtra("MenuItemImage",images)
            requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
return items.size   }

    class PopularViewHolder (private val binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root){
        private val imageView=binding.imageView3
        fun bind(item: String, images: Int, prices: String) {
binding.foodNamePopular.text=item
binding.PricePopular.text=prices
            imageView.setImageResource(images)

        }

    }
}