package com.example.moviebrowser.moviescreen.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.core.networking.RetrofitHelper
import com.example.moviebrowser.core.utils.Constants
import com.example.moviebrowser.core.utils.SearchWidgetState
import com.example.moviebrowser.core.utils.SearchedMoviesState
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import kotlinx.coroutines.launch
import java.net.URLEncoder

class AppViewModel: ViewModel() {

    private val _indexScreen: MutableState<Int> = mutableStateOf(0)
    val indexScreen = _indexScreen

    private val _movieList: MutableState<List<PopularMoviesResponse.Result>> =
        mutableStateOf(listOf())
    val movieList = _movieList

//    val favouriteList = mutableStateOf(listOf<String>())

    private val _searchedMovies: MutableState<List<PopularMoviesResponse.Result>> =
        mutableStateOf(listOf())
    val searchedMovies = _searchedMovies

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _searchedMoviesState: MutableState<SearchedMoviesState> =
        mutableStateOf(value = SearchedMoviesState.HIDE)
    val searchedMoviesState = _searchedMoviesState

    fun updateSearchedMoviesState(newvalue: SearchedMoviesState) {
        _searchedMoviesState.value = newvalue
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    fun onIndexChange(index: Int) {
        _indexScreen.value = index
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            // _isLoading = true
            val response = RetrofitHelper.getRetrofit().getPopularMovies(Constants.API_KEY)
            if(response.isSuccessful) {
                _movieList.value = response.body()!!.results
            }
            // _isLoading = false
        }
    }

//            val encodedQuery = withContext(Dispatchers.IO) {
//                URLEncoder.encode(query, "UTF-8")
//            }
    fun searchMovie(query: String) {
        viewModelScope.launch {
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            val response = RetrofitHelper.getRetrofit().searchMovie(Constants.API_KEY, encodedQuery)
            if (response.isSuccessful) {
                Log.d("gabs", "searchMovie: success")
                _searchedMovies.value = response.body()!!.results
            } else Log.d("gabs", "searchMovie: error")
        }
    }

    /*fun getFavouriteMovies() {
        viewModelScope.launch {

        }
    }*/
}