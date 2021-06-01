package com.weiwei.beats

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.weiwei.beats.database.RestaurantDatabase
import com.weiwei.beats.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sp: SharedPreferences
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //setup navigation controller with the up button
//        navController = this.findNavController(R.id.navController)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        navController = this.findNavController(R.id.navController)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        setupBottomNavigation()

//        //retrieve the database dao
//        val application = requireNotNull(this).application
//        val dataSource = RestaurantDatabase.getInstance(application).restaurantDatabaseDao

        //get the shared viewModel associated with the activity
        viewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)

        //check whether the database is created or not
        //database will be initialized once in this project
        //write a mark to a sharedpreference file
        sp = getPreferences(Context.MODE_PRIVATE)
        val databaseState = sp.getBoolean("Created", false)
        if (!databaseState) {
            viewModel.initDB()
            val editor = sp.edit()
            editor.putBoolean("Created", true)
            editor.apply()
        }


    }

    private fun setupBottomNavigation()
    {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navController) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)

        //var appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homePageFragment, R.id.aboutFragment, R.id.weatherFragment))
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}