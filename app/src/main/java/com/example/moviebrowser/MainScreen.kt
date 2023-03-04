package com.example.moviebrowser

import android.graphics.Movie
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    MyScaffold()
}

@Composable
fun MyScaffold() {
    Scaffold(
        topBar = { MyTopAppBar() },
        bottomBar = { MyBottomNavigation() }
    ) {
        Text("Hola")
    }
}

@Composable
fun MyBottomNavigation() {

    var index by rememberSaveable { mutableStateOf(0) }

    BottomAppBar(backgroundColor = Utils.primaryColor, contentColor = Color.White) {
        BottomNavigationItem(selected = index == 0, onClick = { index = 0 }, icon = {
            Icon(imageVector = Icons.Default.Home, contentDescription = "home")
        }, label = { Text(text = "Home") })

        BottomNavigationItem(selected = index == 1, onClick = { index = 1 }, icon = {
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
            IconButton(onClick = {  } ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }}
    )
}
