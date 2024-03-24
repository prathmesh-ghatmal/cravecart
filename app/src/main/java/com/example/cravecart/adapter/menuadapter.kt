package com.example.cravecart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cravecart.DetailsActivity2
import com.example.cravecart.databinding.MenuItemBinding

class menuadapter(private val menuItemsName:MutableList<String>,private val menuprice:MutableList<String>,private val menuImage:MutableList<Int>,private val requireContext:Context):RecyclerView.Adapter<menuadapter.MenuViewHolder>() {
private val itemClickListener:OnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
holder.bind(position)    }
    override fun getItemCount(): Int =menuItemsName.size
  inner  class MenuViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root) {
      init {
          binding.root.setOnClickListener {
              val Position=adapterPosition
              if(position!=RecyclerView.NO_POSITION){
                  itemClickListener?.onItemClick(position)
              }
              //set on click listner for details code
              val intent=Intent(requireContext,DetailsActivity2::class.java)
              intent.putExtra("MenuItemName",menuItemsName.get(position))
              intent.putExtra("MenuItemImage",menuImage.get(position))
              requireContext.startActivity(intent)
          }
      }
      fun bind(position: Int) {
          binding.apply {
              menufoodName.text=menuItemsName[position]
              menuPrice.text=menuprice[position]
              menuimage.setImageResource(menuImage[position])

          }
      }

  }
    interface OnClickListener{
      fun  onItemClick(position: Int) {

    }
    }
}


