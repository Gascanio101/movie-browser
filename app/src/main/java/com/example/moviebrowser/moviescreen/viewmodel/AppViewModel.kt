package com.example.moviebrowser.moviescreen.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.moviescreen.data.MoviesRepository
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import com.example.moviebrowser.moviescreen.domain.MoviesUseCase
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    private val repository = MoviesRepository()

    private val _popularMovies = MutableLiveData<List<PopularMoviesResponse.Result>>()
    val popularMoviesList: LiveData<List<PopularMoviesResponse.Result>> = _popularMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onIndexChange(index: Int) {
        _index.value = index
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            // _isLoading = true
            val popularMoviesResponse = repository.getPopularMovies()
            if (popularMoviesResponse != null) {
                _popularMovies.postValue(popularMoviesResponse.results)
            } else Log.i("gabs", "error loading movies.")
            // _isLoading = false
        }
    }
}