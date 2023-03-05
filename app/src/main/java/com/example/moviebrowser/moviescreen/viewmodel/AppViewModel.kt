package com.example.moviebrowser.moviescreen.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.core.networking.RetrofitHelper
import com.example.moviebrowser.core.utils.Constants
import com.example.moviebrowser.moviescreen.data.MoviesRepository
import com.example.moviebrowser.moviescreen.data.networking.MovieService
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    private val repository = MoviesRepository()


    private val _popularMovies = MutableLiveData<List<PopularMoviesResponse.Result>>()
    val popularMoviesList: LiveData<List<PopularMoviesResponse.Result>> = _popularMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val movieList = mutableStateOf(listOf<PopularMoviesResponse.Result>())



    fun onIndexChange(index: Int) {
        _index.value = index
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            // _isLoading = true
            val response = RetrofitHelper.getRetrofit().getPopularMovies(Constants.API_KEY)
            if(response.isSuccessful) {
                _popularMovies.postValue(response.body()!!.results)
                movieList.value = response.body()!!.results
            }
            // _isLoading = false
        }
    }
}