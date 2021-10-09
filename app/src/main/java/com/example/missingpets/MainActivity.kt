package com.example.missingpets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.missingpets.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.annotation.NonNull
import com.example.missingpets.fragments.FoundFragment
import com.example.missingpets.fragments.LogInFragment
import com.example.missingpets.fragments.MissingFragment
import com.example.missingpets.fragments.NewPostFragment

import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val missingFragment = MissingFragment()
        val foundFragment = FoundFragment()
        val newPostFragment = NewPostFragment()

        replaceFragment(missingFragment)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.id_missing -> replaceFragment(missingFragment)
                R.id.id_found -> replaceFragment(foundFragment)
                R.id.id_add_notification -> replaceFragment(newPostFragment)
            }
            true
        }

        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            commit()
        }
    }


}
