package com.example.moviebrowser.moviescreen.data.networking

import android.graphics.Movie
import com.example.moviebrowser.moviescreen.data.networking.response.MovieDetails
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesClient {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int = 1
    ): Response<PopularMoviesResponse>

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Response<PopularMoviesResponse>

    @GET("3/movie/{id}")
    suspend fun searchMovieById(
        @Path("id") id: Int,
        @Query("api_key") api_key: String
    ): Response<PopularMoviesResponse.Result>
}