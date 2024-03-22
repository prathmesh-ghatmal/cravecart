package com.example.cravecart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cravecart.databinding.NotificationItemBinding

class notificationadapter(private var notification:ArrayList<String>,private var notificationimage:ArrayList<Int>):RecyclerView.Adapter<notificationadapter.notificationviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notificationviewHolder {
        val binding=NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return notificationviewHolder(binding)
    }



    override fun onBindViewHolder(holder: notificationviewHolder, position: Int) {
       holder.bind(position)
    }
    override fun getItemCount(): Int =notification.size
    inner  class notificationviewHolder (private val  binding: NotificationItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                Notificationtextview.text=notification[position]
                NotificationImageVIew.setImageResource(notificationimage[position])
            }
        }

    }

}