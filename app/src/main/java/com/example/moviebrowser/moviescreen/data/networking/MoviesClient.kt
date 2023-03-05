package com.example.moviebrowser.moviescreen.data.networking

import com.example.moviebrowser.core.utils.Constants.Companion.API_KEY
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesClient {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("api_key") api_key: String): Response<PopularMoviesResponse?>
}