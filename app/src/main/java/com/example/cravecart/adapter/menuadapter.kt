package com.example.cravecart.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cravecart.databinding.MenuItemBinding

class menuadapter(private val menuItemsName:MutableList<String>,private val menuprice:MutableList<String>,private val menuImage:MutableList<Int>,):RecyclerView.Adapter<menuadapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
val binding=MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
holder.bind(position)    }
    override fun getItemCount(): Int =menuItemsName.size
  inner  class MenuViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root) {
      fun bind(position: Int) {
          binding.apply {
              menufoodName.text=menuItemsName[position]
              menuPrice.text=menuprice[position]
              menuimage.setImageResource(menuImage[position])
          }
      }

  }
}