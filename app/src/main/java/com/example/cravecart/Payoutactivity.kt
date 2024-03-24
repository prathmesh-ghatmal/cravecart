package com.example.cravecart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.cravecart.Fragment.CartFragment
import com.example.cravecart.Fragment.CongratsBottomSheetFragment
import com.example.cravecart.databinding.ActivityPayoutactivityBinding

class Payoutactivity : AppCompatActivity() {
    lateinit var binding: ActivityPayoutactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayoutactivityBinding.inflate(layoutInflater)
        binding.PlaceOrderBackButton.setOnClickListener {
            var intent= Intent(this,CartFragment::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)

        binding.PlaceMyOrder.setOnClickListener {
            val bottomsheetDialog=CongratsBottomSheetFragment()
            bottomsheetDialog.show(supportFragmentManager,"Test")
        }
    }
}