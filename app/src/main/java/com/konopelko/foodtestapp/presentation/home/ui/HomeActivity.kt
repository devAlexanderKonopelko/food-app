package com.konopelko.foodtestapp.presentation.home.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.konopelko.foodtestapp.R
import dagger.hilt.android.AndroidEntryPoint

//TODO: create NavigationManager handing screens navigation

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initHomeNavGraph()
    }

    private fun initHomeNavGraph() {
        (supportFragmentManager.findFragmentById(R.id.home_fragment_container_view) as? NavHostFragment)
            ?.navController
            ?.setGraph(R.navigation.nav_graph_home)
    }
}