package com.example.cravecart.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.R
import com.example.cravecart.adapter.notificationadapter
import com.example.cravecart.databinding.FragmentNotificationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomSheetFragment : BottomSheetDialogFragment() {

   private lateinit var binding: FragmentNotificationBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNotificationBottomSheetBinding.inflate(layoutInflater,container,false)
        val notifications= listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
        val notificationImages= listOf(R.drawable.sademoji, R.drawable.truck, R.drawable.congrats)
        val adapter=notificationadapter(ArrayList(notifications),ArrayList(notificationImages))
        binding.notificationRecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.notificationRecyclerview.adapter=adapter
        return binding.root
    }

    companion object {


    }
}