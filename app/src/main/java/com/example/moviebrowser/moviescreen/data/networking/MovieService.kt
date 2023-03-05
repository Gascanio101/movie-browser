package com.example.moviebrowser.moviescreen.data.networking

import androidx.compose.runtime.Composable
import com.example.moviebrowser.core.networking.RetrofitHelper
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getPopularMovies(): PopularMoviesResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(MoviesClient::class.java).getPopularMovies()
            response.body()
        }
    }
}