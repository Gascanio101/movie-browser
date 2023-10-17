package com.example.moviebrowser.moviescreen.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviebrowser.R
import com.example.moviebrowser.core.utils.Colors.Companion.primaryColor
import com.example.moviebrowser.core.utils.Colors.Companion.secondaryColor
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import com.example.moviebrowser.moviescreen.viewmodel.AppViewModel

@Composable
fun DetailScreen(navController: NavController, vm: AppViewModel) {
    Card(
        Modifier
            .fillMaxSize()
            .background(color = secondaryColor)
            .padding(16.dp)
    ,border = BorderStroke(2.dp, color = primaryColor)
    ,shape = RoundedCornerShape(16.dp)){
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            AsyncImage(
                model = R.drawable.movie_placeholder,
                contentDescription = "Movie poster"
            )
        }
    }
}