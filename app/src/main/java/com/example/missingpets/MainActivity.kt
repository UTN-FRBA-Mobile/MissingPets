package com.example.missingpets

import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences

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

    override fun onResume() {
        super.onResume()

      //  refreshSettingsPreferences()
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

    // desactivado falta inicializar para que no cuelgue el inicio //
    private fun refreshSettingsPreferences() {

        val imgSize = sharedPreferences.getString( getString(R.string.preferences_key_ui_img_size),"")
        val sizeValue = when(imgSize){
            getString(R.string.preferences_key_img_size_small) -> {
                resources.getDimensionPixelSize(R.dimen.profile_image_size_small)
            }
            getString(R.string.preferences_key_img_size_medium) -> {
                resources.getDimensionPixelSize(R.dimen.profile_image_size_medium)
            }
            else -> resources.getDimensionPixelSize(R.dimen.profile_image_size_large)
        }
        /* aca asigno las medidas a la imagen . Falta ver a que imagen ?
        binding.item_image.updateLayoutParams {
            width = sizeValue
            height = sizeValue
        } */
    }

}
