package com.example.cravecart.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cravecart.Model.UserModel
import com.example.cravecart.R
import com.example.cravecart.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setUserData()
        binding.apply {
            ProfilePhone.isEnabled=false
            ProfileEmail.isEnabled=false
            ProfileName.isEnabled=false
            ProfileAddress.isEnabled=false
            binding.EditButton.setOnClickListener {
               ProfilePhone.isEnabled=!ProfilePhone.isEnabled
               ProfileName.isEnabled=!ProfileName.isEnabled
               ProfileEmail.isEnabled=!ProfileEmail.isEnabled
               ProfileAddress.isEnabled=!ProfileAddress.isEnabled
            }
        }
        binding.SaveInformationButton.setOnClickListener {
            val name=binding.ProfileName.text.toString()
            val email=binding.ProfileEmail.text.toString()
            val address=binding.ProfileAddress.text.toString()
            val phone=binding.ProfilePhone.text.toString()
            updateUserData(name,email,address,phone)
            binding.apply {  ProfilePhone.isEnabled=false
                ProfileEmail.isEnabled=false
                ProfileName.isEnabled=false
                ProfileAddress.isEnabled=false }
        }

        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId=auth.currentUser?.uid
        if(userId!=null){
            val userReference:DatabaseReference=database.getReference("user").child(userId)
            val userData= hashMapOf(
                "name" to name,"email" to email,"address" to address,"phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"profile updated sucessfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"profile updation failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRefrence = database.getReference("user").child(userId)

            userRefrence.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userProfile=snapshot.getValue(UserModel::class.java)
                        if (userProfile != null){
                            binding.ProfileName.setText(userProfile.name)
                            binding.ProfileAddress.setText(userProfile.address)
                            binding.ProfileEmail.setText(userProfile.email)
                            binding.ProfilePhone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }


}