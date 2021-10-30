package com.example.missingpets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.missingpets.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val configurationFragment = ConfigurationFragment()
        val aboutUsFragment = AboutUsFragment()

        when (item.itemId) {

            R.id.action_settings -> replaceFragment(configurationFragment)
            R.id.action_about -> replaceFragment(aboutUsFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}
