package com.example.cravecart.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.R
import com.example.cravecart.adapter.Buyagainadapter
import com.example.cravecart.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
   private lateinit var binding: FragmentHistoryBinding
   private lateinit var buyagainadapter: Buyagainadapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHistoryBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        setupRecyclerview()
        return binding.root
    }
    private fun setupRecyclerview(){
        val buyAgainFoodname= arrayListOf("food1","food2","food3")
        val buyAgainFoodprice= arrayListOf("Rs10","Rs20","Rs30")
        val buyAgainFoodimage= arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3)
        buyagainadapter= Buyagainadapter(buyAgainFoodname,buyAgainFoodprice,buyAgainFoodimage)
        binding.BuyAgainRecyclerView.adapter=buyagainadapter
        binding.BuyAgainRecyclerView.layoutManager=LinearLayoutManager(requireContext())
    }

    companion object {

    }
}