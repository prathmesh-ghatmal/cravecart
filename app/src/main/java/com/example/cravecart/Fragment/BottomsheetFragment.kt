package com.example.cravecart.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.R
import com.example.cravecart.adapter.menuadapter
import com.example.cravecart.databinding.FragmentBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomsheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomsheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomsheetBinding.inflate(inflater, container, false)
        binding.buttonbackmenu.setOnClickListener {
            dismiss()
        }
        val mfoodname = listOf(
            "burger",
            "pizza",
            "momo",
            "mocha",
            "sandwich",
            "gg",
            "burger",
            "pizza",
            "momo",
            "mocha",
            "sandwich",
            "gg"
        )
        val mprice = listOf(
            "Rs25",
            "Rs85",
            "Rs95",
            "Rs125",
            "Rs15",
            "Rs75",
            "Rs25",
            "Rs85",
            "Rs95",
            "Rs125",
            "Rs15",
            "Rs75"
        )
        val mimage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6, R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )
        val adapter = menuadapter(ArrayList(mfoodname), ArrayList(mprice), ArrayList(mimage))
        binding.menurecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter
        return binding.root
    }

    companion object {

    }
}