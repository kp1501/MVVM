package com.khush.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.appizona.yehiahd.fastsave.FastSave
import com.khush.myapplication.R
import com.khush.myapplication.util.Constants.IS_USER_LOGIN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (FastSave.getInstance().getBoolean(IS_USER_LOGIN, false)){
            graph.setStartDestination(R.id.userListFragment)
        }else {
            graph.setStartDestination(R.id.boardingFragment)
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }
}