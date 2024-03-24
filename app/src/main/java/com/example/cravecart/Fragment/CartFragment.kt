package com.example.cravecart.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.Payoutactivity
import com.example.cravecart.R
import com.example.cravecart.adapter.cartadapter
import com.example.cravecart.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val cfoodname= listOf("burger","pizza","momo","mocha","sandwich","gg")
        val cprice= listOf("Rs25","Rs85","Rs95","Rs125","Rs15","Rs75")
        val cimage= listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )
        val adapter=cartadapter(ArrayList(cfoodname),ArrayList(cprice),ArrayList(cimage))
        binding.cartrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.cartrecyclerview.adapter=adapter
        binding.Proceedbutton.setOnClickListener {
            val intent=Intent(requireContext(),Payoutactivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {

    }
}