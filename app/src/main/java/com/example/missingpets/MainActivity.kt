package com.example.missingpets

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.missingpets.databinding.ActivityMainBinding
import com.example.missingpets.network.ApiClient
import com.example.missingpets.viewModels.UserProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    val user: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonNavegationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)  as NavHostFragment
        val navController = navHostFragment.navController
        botonNavegationView.setupWithNavController(navController)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.fragment)
        when (item.itemId) {

          R.id.action_logout ->{
              Toast.makeText(this,"Hacer logout",Toast.LENGTH_SHORT).show()
              user.id = -1;
              navController.navigate(R.id.mainFragment)
          }
        }
        return  item.onNavDestinationSelected(navController)|| super.onOptionsItemSelected(item)
    }



}
