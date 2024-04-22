package com.example.cravecart.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.Model.MenuItem
import com.example.cravecart.R
import com.example.cravecart.adapter.menuadapter
import com.example.cravecart.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {

private lateinit var binding: FragmentSearchBinding
private lateinit var adapter: menuadapter
 private lateinit var database:FirebaseDatabase
 private lateinit var auth: FirebaseAuth
 private val originalMenuItem= mutableListOf<MenuItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)

      //retrie menu items
        retriveMenuItem()

        //setup for searchview
        setupSearchview()
        // show all menuitems

        return binding.root
    }

    private fun retriveMenuItem() {
        //get database reference
        database= FirebaseDatabase.getInstance()
        //reference of mmenu node
        val foodReference:DatabaseReference=database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children){
                    val menuItem=foodSnapShot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItem.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun showAllMenu() {
        val filteredMenuItem=ArrayList(originalMenuItem)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: java.util.ArrayList<MenuItem>) {
        adapter= menuadapter(filteredMenuItem,requireContext())
        binding.menurecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter=adapter
    }


    private fun setupSearchview() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {

     val filteredMenuItemss=originalMenuItem.filter {
         it.foodName?.contains(query,ignoreCase = true)==true
     }
     setAdapter(filteredMenuItemss as java.util.ArrayList<MenuItem>)
    }

    companion object {

    }
}