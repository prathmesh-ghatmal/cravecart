package com.example.cravecart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.cravecart.databinding.ActivityChooselocationBinding

class Chooselocation : AppCompatActivity() {
    private val binding:ActivityChooselocationBinding by lazy {
        ActivityChooselocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locati_onlist= arrayOf("jaipur","odisa","mumbai")
        val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,locati_onlist)
        val autoCompleteTextView=binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)

    }
}