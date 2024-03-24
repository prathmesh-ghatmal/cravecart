package com.example.cravecart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cravecart.Fragment.CongratsBottomSheetFragment
import com.example.cravecart.databinding.ActivityPayoutactivityBinding

class Payoutactivity : AppCompatActivity() {
    lateinit var binding: ActivityPayoutactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayoutactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.PlaceMyOrder.setOnClickListener {
            val bottomsheetDialog=CongratsBottomSheetFragment()
            bottomsheetDialog.show(supportFragmentManager,"Test")
        }
    }
}