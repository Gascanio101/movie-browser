package com.example.moviebrowser.moviescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.moviebrowser.composableUtils.MyScaffold
import com.example.moviebrowser.core.utils.Colors.Companion.backgroundColor
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel

@Composable
fun HomeScreen(navController: NavController, vm: AppViewModel) {
//    vm.getPopularMovies()
    MyScaffold(navigationController = navController, vm)
}

@Composable
fun HomeBox(vm: AppViewModel) {
//    val movies: List<PopularMoviesResponse.Result> = vm.popularMoviesList.value ?: emptyList()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)) {
        LazyColumn() {
            items(vm.popularMoviesList.value) { movie ->
                Text("Movie name: ${movie.originalTitle}")
            }
        }
    }
}

/*@Composable
fun HomeBox(vm: AppViewModel) {
    val movies = vm.popularMoviesList.value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)) {
        LazyColumn() {
            items(movies!!.size) {
                Text("Movie name: ${}")
            }
        }
        *//*movies.value!!.forEach {
            Text(it.originalTitle, Modifier.padding(8.dp))
        }*//*
//        Text(text = "Popular Movies", modifier = Modifier.align(Alignment.Center) )
    }
}*/

@Composable
fun FavouriteBox() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)) {
        Text(text = "Favourite Movies", modifier = Modifier.align(Alignment.Center) )
    }
}