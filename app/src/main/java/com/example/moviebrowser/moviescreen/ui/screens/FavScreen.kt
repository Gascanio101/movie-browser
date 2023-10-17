package com.example.moviebrowser.moviescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviebrowser.composableUtils.MyScaffold
import com.example.moviebrowser.core.utils.Colors
import com.example.moviebrowser.moviescreen.ui.elements.MovieItem
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel

@Composable
fun FavScreen(navController: NavController, vm: AppViewModel) {
    MyScaffold(navigationController = navController, vm = vm)
}

@Composable
fun FavouriteBox(vm: AppViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.backgroundColor)
            .padding(bottom = 48.dp)
    ) {
        when (vm.isLoading.value) {
            true -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Colors.primaryColor)
                }
            }
            false -> {
                if (vm.favouriteMovieList.value.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No favourite movies, add some!")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 16.dp)) {
                        items(vm.favouriteMovieList.value) { movie ->
                            if (vm.favouriteIdList.value.contains(movie.id)) {
                                vm.showFavourite(true)
                                MovieItem(movie, vm.isFavourite.value) {
                                    vm.saveMovie(it)
                                    vm.isFavourite.value = false
                                }
                            } else MovieItem(movie) {
                                vm.saveMovie(it)
                                vm.isFavourite.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}
