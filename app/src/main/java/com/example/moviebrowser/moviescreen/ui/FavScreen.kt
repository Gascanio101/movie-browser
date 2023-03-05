package com.example.moviebrowser.moviescreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.moviebrowser.composableUtils.MyScaffold
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel

@Composable
fun FavScreen(navController: NavController, vm: AppViewModel) {
    MyScaffold(navigationController = navController, vm = vm)
}