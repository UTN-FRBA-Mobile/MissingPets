package com.example.missingpets

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.missingpets.databinding.ActivityMainBinding
import com.example.missingpets.network.BackendApi
import com.example.missingpets.network.MissingPet
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val botonNavegationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)  as NavHostFragment
        val navController = navHostFragment.navController
        botonNavegationView.setupWithNavController(navController)

        loadMissingPets()
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


    private fun loadMissingPets() {
        // Launch Kotlin Coroutine on Android's main thread
        // Note: better not to use GlobalScope, see:
        // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/index.html
        // An even better solution would be to use the Android livecycle-aware viewmodel
        // instead of attaching the scope to the activity.
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Execute web request through coroutine call adapter & retrofit
                val webResponse = BackendApi.retrofitService.getLost() //.await()

                Log.d("NETWORK", "Ejecutando llamada a la API REST")

                if (webResponse.isNotEmpty()) {
                    // Get the returned & parsed JSON from the web response.
                    // Type specified explicitly here to make it clear that we already
                    // get parsed contents.
                    val lost: List<MissingPet>? = webResponse.toList()
                    Log.d("NETWORK", lost.toString())
                    // Assign the list to the recycler view. If partsList is null,
                    // assign an empty list to the adapter.
                    /*adapter.partItemList = partList ?: listOf()*/
                    // Inform recycler view that data has changed.
                    // Makes sure the view re-renders itself
                    /*adapter.notifyDataSetChanged()*/
                } else {
                    // Print error information to the console
                    Log.e("NETWORK", "Error ${webResponse.toString()}")
                    Toast.makeText(this@MainActivity, "Error ${webResponse.toString()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                // Error with network request
                Log.e("NETWORK", "Exception " + e.printStackTrace())
                Toast.makeText(this@MainActivity, "Exception ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }



}
