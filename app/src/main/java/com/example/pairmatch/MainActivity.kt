package com.example.pairmatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.pairmatch.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLoggedUser()
    }

    private fun checkLoggedUser() {
        if (user != null) {
            finish()
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
    }


    override fun onBackPressed() {
        val currentFragment: Fragment = supportFragmentManager.fragments[0] ?: return
        val controller = Navigation.findNavController(this, R.id.nav_host_fragment)
        if (currentFragment is OnBackPressed) (currentFragment as OnBackPressed).onBackPressed()
        else if (!controller.popBackStack()) finish()
    }
}