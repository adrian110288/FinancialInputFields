package com.lesniak.financialinputfields

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.lesniak.financialinputfields.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = setContentView(this, R.layout.activity_main)

        binding.model = model
        binding.lifecycleOwner = this
        binding.amountEt.requestFocus()
    }
}