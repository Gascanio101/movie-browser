package com.example.moviebrowser.composableUtils

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.moviebrowser.Utils
import com.example.moviebrowser.screens.*

@Composable
fun MyScaffold(navigationController: NavController, vm: AppViewModel) {

    Scaffold(
        topBar = { MyTopAppBar() },
        bottomBar = { MyBottomNavigation(navigationController, vm) }
    ) {
        when (vm.index.value) {
            0 -> HomeBox()
            1 -> FavouriteBox()
        }
    }
}

@Composable
fun MyBottomNavigation(navigationController: NavController, vm: AppViewModel) {

    val index: Int by vm.index.observeAsState(initial = 0)
    
    BottomAppBar(backgroundColor = Utils.primaryColor, contentColor = Color.White) {
        BottomNavigationItem(
            selected = index == 0,
            onClick = {
                vm.onIndexChange(0)
                navigationController.navigate("popularMovies")
            }, icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "home")
            }, label = { Text(text = "Home") })

        BottomNavigationItem(
            selected = index == 1,
            onClick = {
                vm.onIndexChange(1)
                navigationController.navigate("favouriteMovies")
            }, icon = {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "favourites")
            }, label = { Text(text = "Favourites") })
    }
}

@Composable
fun MyTopAppBar() {
    TopAppBar(
        backgroundColor = Utils.primaryColor,
        contentColor = Color.White,
        title = { Text(text = "Movie Browser") },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            }
        }
    )
}