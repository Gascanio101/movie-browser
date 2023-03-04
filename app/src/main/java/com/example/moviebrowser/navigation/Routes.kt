package com.example.moviebrowser.navigation

sealed class Routes(val route: String) {
    object Home: Routes("popularMovies")
    object Favourite: Routes("favouriteMovies")
}
