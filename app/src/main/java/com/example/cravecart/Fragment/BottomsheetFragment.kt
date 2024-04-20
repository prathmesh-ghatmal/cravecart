package com.example.cravecart.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.Model.MenuItem
import com.example.cravecart.R
import com.example.cravecart.adapter.menuadapter
import com.example.cravecart.databinding.FragmentBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BottomsheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomsheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>
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
         retriveMenuItems()

        return binding.root
    }

    private fun retriveMenuItems() {
        database=FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems= mutableListOf()

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                //loop through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)

                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error${error.message}")
            }


        })

    }
    private fun setAdapter() {
        val adapter=menuadapter(menuItems,requireContext())
        binding.menurecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter    }

    companion object {

    }
}