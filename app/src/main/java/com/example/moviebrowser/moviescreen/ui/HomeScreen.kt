package com.example.moviebrowser.moviescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviebrowser.composableUtils.MyScaffold
import com.example.moviebrowser.core.utils.Colors.Companion.backgroundColor
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel
import androidx.compose.ui.res.painterResource

import android.graphics.Paint.Align
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviebrowser.R
import kotlin.math.exp

@Composable
fun HomeScreen(navController: NavController, vm: AppViewModel) {
    vm.getPopularMovies()
    MyScaffold(navigationController = navController, vm)
}

@Composable
fun HomeBox(vm: AppViewModel) {
    Log.d("gabs", "HomeBox: first access")
//    val movies: List<PopularMoviesResponse.Result> = vm.popularMoviesList.value ?: emptyList()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        vm.movieList.value.forEach {
            MovieItem(it)
        }
        /*LazyColumn() {
            items() { movie ->
                Text("Movie name: ${movie.originalTitle}")
            }
        }*/
    }
}

@Composable
fun MovieItem(movie: PopularMoviesResponse.Result) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column() {
            // TODO: Add image URL constructor from TMDB and assign it to a val.
            /*Image(
                painter = painterResource(id = R),
                contentDescription = "Hero avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )*/
            Image(
                painter = painterResource(id = R.drawable.movie_placeholder),
                contentDescription = "placeholder",
            )
            Text(text = movie.originalTitle, fontSize = 24.sp, fontWeight = FontWeight.Bold)

        }
    }
}

@Composable
fun FavouriteBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Text(text = "Favourite Movies", modifier = Modifier.align(Alignment.Center))
    }
}