package com.example.moviebrowser.moviescreen.data.networking

import com.example.moviebrowser.core.utils.Constants.Companion.API_KEY
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesClient {
    @GET("3/movie/popular?api_key=$API_KEY")
    fun getPopularMovies(): Response<PopularMoviesResponse?>
}