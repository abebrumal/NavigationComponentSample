package com.catalyst.navigationcomponentsample

import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appbarConfig: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHost
        navController = navHostFragment.navController
        navigationView = findViewById(R.id.navigationView)
        navigationView.setupWithNavController(navController)

        drawerLayout = findViewById(R.id.drawerLayout)
        appbarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appbarConfig)

        listener  = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.firstFragment -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.design_default_color_primary_variant)))
                }
                R.id.secondFragment -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.design_default_color_primary)))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appbarConfig) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }


}