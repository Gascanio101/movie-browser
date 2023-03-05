package com.example.moviebrowser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel
import com.example.moviebrowser.moviescreen.FavScreen
import com.example.moviebrowser.moviescreen.HomeScreen

@Composable
fun AppNavigation(vm: AppViewModel) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) { HomeScreen(navigationController, vm) }
        composable(Routes.Favourite.route) { FavScreen(navigationController, vm)}
    }
}