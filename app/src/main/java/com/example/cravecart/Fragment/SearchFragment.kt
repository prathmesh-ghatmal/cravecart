package com.example.cravecart.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cravecart.R
import com.example.cravecart.adapter.menuadapter
import com.example.cravecart.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

private lateinit var binding: FragmentSearchBinding
private lateinit var adapter: menuadapter
  private  val originalfoodname= listOf("burger","pizza","momo","mocha","sandwich","gg")
   private val originalprice= listOf("Rs25","Rs85","Rs95","Rs125","Rs15","Rs75")
    private val originalimage= listOf(
        R.drawable.menu1,
        R.drawable.menu2,
        R.drawable.menu3,
        R.drawable.menu4,
        R.drawable.menu5,
        R.drawable.menu6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val filteredmMenuFoodName= mutableListOf<String>()
    private val filteredmMenuFoodPrice= mutableListOf<String>()
    private val filteredmMenuFoodImage= mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater,container,false)
        adapter= menuadapter(filteredmMenuFoodName,filteredmMenuFoodPrice,filteredmMenuFoodImage,requireContext())
        binding.menurecyclerview.layoutManager=LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter=adapter


        //setup for searchview
        setupSearchview()
        // show all menuitems
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filteredmMenuFoodName.clear()
        filteredmMenuFoodPrice.clear()
        filteredmMenuFoodImage.clear()

        filteredmMenuFoodName.addAll(originalfoodname)
        filteredmMenuFoodPrice.addAll(originalprice)
        filteredmMenuFoodImage.addAll(originalimage)
        adapter.notifyDataSetChanged()
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
       filteredmMenuFoodName.clear()
       filteredmMenuFoodPrice.clear()
       filteredmMenuFoodImage.clear()

        originalfoodname.forEachIndexed { index, foodname ->
            if (foodname.contains(query,ignoreCase = true)){
                filteredmMenuFoodName.add(foodname)
                filteredmMenuFoodPrice.add(originalprice[index])
                filteredmMenuFoodImage.add(originalimage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }

    companion object {

    }
}