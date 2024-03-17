package com.example.cravecart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cravecart.databinding.CartItemBinding

class cartadapter(
    private val cartItems: MutableList<String>,
    private val cartItemPrice: MutableList<String>,
    private val cartImage: MutableList<Int>
) : RecyclerView.Adapter<cartadapter.CartViewHolder>() {

    private val itemQuantities = IntArray(cartItems.size) { 1 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size
    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                val quantity = itemQuantities[position]
                foodname.text = cartItems[position]
                price.text = cartItemPrice[position]
                foodimage.setImageResource(cartImage[position])
                foodquantity.text = quantity.toString()

                minusbutton.setOnClickListener{decreasequantity(position)}
                plusbutton.setOnClickListener{increasequantity(position)}
                deletebutton.setOnClickListener{
                    val itemPosition=adapterPosition
                    if (itemPosition!= RecyclerView.NO_POSITION){
                        deleteitem(itemPosition)
                    }
                }
            }
        }
        private fun increasequantity(position: Int){
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.foodquantity.text=itemQuantities[position].toString()
            }
        }
        private fun decreasequantity(position: Int){
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                binding.foodquantity.text=itemQuantities[position].toString()
            }
        }
        private fun deleteitem(position: Int){
            cartItems.removeAt(position)
            cartImage.removeAt(position)
            cartItemPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartItems.size)
        }

    }
}