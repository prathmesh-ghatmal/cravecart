package com.example.cravecart.Fragment

import android.os.Bundle
import android.os.TestLooperManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.cravecart.Model.MenuItem
import com.example.cravecart.R
import com.example.cravecart.adapter.Popularadapter
import com.example.cravecart.adapter.menuadapter
import com.example.cravecart.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewmenu.setOnClickListener {
            val bottomSheetDialog = BottomsheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        //retrive popular
        retriveAndDisplayPopular()
        return binding.root


    }

    private fun retriveAndDisplayPopular() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                //loop through each food item
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)

                    menuItem?.let {
                        menuItems.add(it)
                    }
                    randomPopular()
                }

            }


            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error${error.message}")
            }


        })
    }

    private fun randomPopular() {
     //create shuffled list of menuitems
        val index=menuItems.indices.toList().shuffled()
        val numofitemTOshow=5
        val subsetMenuItems=index.take(numofitemTOshow).map { menuItems[it] }

        setPopularItemAdapter(subsetMenuItems)
    }

    private fun setPopularItemAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter = menuadapter(subsetMenuItems, requireContext())
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagelist = ArrayList<SlideModel>()
        imagelist.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imagelist.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imagelist.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imagelist)
        imageSlider.setImageList(imagelist, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imagelist[position]
                val itemMessage = "selected option $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })
        val foodname = listOf("burger", "sandwich", "momo", "pizza")
        val price = listOf("Rs25", "Rs25", "Rs25", "Rs25")
        val popularimages =
            listOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4)


    }

    companion object {

    }
}