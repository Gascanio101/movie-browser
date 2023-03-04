package com.example.moviebrowser.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.moviebrowser.Utils
import com.example.moviebrowser.composableUtils.MyScaffold

@Composable
fun HomeScreen(navController: NavController, vm: AppViewModel) {
    MyScaffold(navigationController = navController, vm)
}

@Composable
fun HomeBox() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Utils.backgroundColor)) {
        Text(text = "Popular Movies", modifier = Modifier.align(Alignment.Center) )
    }
}

@Composable
fun FavouriteBox() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Yellow)) {
        Text(text = "Favourite Movies", modifier = Modifier.align(Alignment.Center) )
    }
}