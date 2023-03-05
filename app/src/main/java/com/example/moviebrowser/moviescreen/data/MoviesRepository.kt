package com.example.moviebrowser.moviescreen.data

import com.example.moviebrowser.moviescreen.data.networking.MovieService
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse

class MoviesRepository {
    private val api = MovieService()
}