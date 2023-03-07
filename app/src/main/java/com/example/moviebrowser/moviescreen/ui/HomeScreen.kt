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
import android.widget.ProgressBar
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
import coil.compose.AsyncImage
import com.example.moviebrowser.R
import com.example.moviebrowser.core.utils.Colors.Companion.primaryColor
import com.example.moviebrowser.core.utils.SearchedMoviesState
import com.example.moviebrowser.moviescreen.data.networking.response.MovieDetails
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun HomeScreen(navController: NavController, vm: AppViewModel) {
    vm.getPopularMovies()
    MyScaffold(navigationController = navController, vm)
}

@Composable
fun HomeBox(vm: AppViewModel, searchedMoviesState: SearchedMoviesState) {
    Log.d("gabs", "HomeBox: first access")
//    val movies: List<PopularMoviesResponse.Result> = vm.popularMoviesList.value ?: emptyList()
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

@Composable
fun FavouriteBox(vm: AppViewModel) {
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
                if (vm.favouriteMovieList.value.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
                        Text("No favourite movies, add some!")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
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

@Composable
fun MovieItem(
    movie: PopularMoviesResponse.Result,
    isFavourite: Boolean = false,
    onFavouriteClick: (Int) -> Unit
) {
    var localIsFavourite by remember { mutableStateOf(isFavourite) }
    val context = LocalContext.current

    Card(
        Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        border = BorderStroke(2.dp, color = primaryColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryColor)
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val imageUrl = "https://image.tmdb.org/t/p/w1280" + movie.backdropPath
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            val roundedVoteAverage = df.format(movie.voteAverage)

            AsyncImage(
                model = imageUrl,
                contentDescription = "movie image",
                placeholder = painterResource(R.drawable.movie_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(primaryColor)
            ) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$roundedVoteAverage/10",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    when (localIsFavourite) {
                        true -> Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "favourite",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                localIsFavourite = !localIsFavourite
                                onFavouriteClick(movie.id)
                                Toast.makeText(context, "Removed from favourites!", Toast.LENGTH_SHORT).show()
                            }
                        )
                        false -> Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "favourite",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                localIsFavourite = !localIsFavourite
                                onFavouriteClick(movie.id)
                                Toast.makeText(context, "Added to favourites!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

