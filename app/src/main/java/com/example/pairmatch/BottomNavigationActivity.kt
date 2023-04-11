package com.example.pairmatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.pairmatch.databinding.ActivityBottomNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(binding.bnv, navController)
    }

    fun showBnv(){
        binding.bnv.isVisible = true
    }

    fun hideBnv(){
        binding.bnv.isVisible = false
    }
}