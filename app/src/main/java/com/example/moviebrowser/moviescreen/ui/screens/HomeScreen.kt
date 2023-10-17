package com.example.moviebrowser.moviescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviebrowser.composableUtils.MyScaffold
import com.example.moviebrowser.core.utils.Colors.Companion.backgroundColor
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.moviebrowser.R
import com.example.moviebrowser.core.utils.Colors.Companion.primaryColor
import com.example.moviebrowser.core.utils.SearchedMoviesState
import java.math.RoundingMode
import java.text.DecimalFormat
import coil.compose.AsyncImage
import com.example.moviebrowser.moviescreen.ui.elements.MovieItem

@Composable
fun HomeScreen(navController: NavController, vm: AppViewModel) {
    vm.getPopularMovies()
    MyScaffold(navigationController = navController, vm)
}

@Composable
fun HomeBox(vm: AppViewModel, searchedMoviesState: SearchedMoviesState) {
    Log.d("gabs", "HomeBox: first access")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(bottom = 48.dp)
    ) {
        when (vm.isLoading.value) {
            true -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                    CircularProgressIndicator(color = primaryColor)
                }
            }
            false -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    when (searchedMoviesState) {
                        SearchedMoviesState.HIDE -> {
                            items(vm.movieList.value.sortedByDescending { it.voteAverage }) { movie ->
                                if (vm.favouriteIdList.value.contains(movie.id)) {
                                    MovieItem(movie, true) { vm.saveMovie(it) }
                                } else MovieItem(movie) { vm.saveMovie(it) }

                            }
                        }
                        SearchedMoviesState.SHOW -> {
                            items(vm.searchedMovies.value) { movie ->
                                MovieItem(movie) { vm.saveMovie(it) }
                            }
                        }
                    }
                }
            }
        }
    }
}


