package com.example.moviebrowser.moviescreen.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.core.networking.RetrofitHelper
import com.example.moviebrowser.core.utils.Constants
import com.example.moviebrowser.core.utils.SearchWidgetState
import com.example.moviebrowser.core.utils.SearchedMoviesState
import com.example.moviebrowser.moviescreen.data.networking.response.PopularMoviesResponse
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.util.*

class AppViewModel(app: Application) : AndroidViewModel(app) {

    private val sharedPreferences =
        app.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun saveMovie(id: Int) {
        val allEntries = sharedPreferences.all
        val idList = allEntries.values.mapNotNull { it as? Int }

        if (idList.contains(id)) {
            // The ID already exists, so remove it
            val uuidToDelete = allEntries.filterValues { it == id }.keys.first()
            val editor = sharedPreferences.edit()
            editor.remove(uuidToDelete)
            editor.apply()
            favouriteMovieList.value = favouriteMovieList.value.filter { it.id != id }
            Log.d("gabs", "onCreate: Movie already saved! DELETED!!!")
        } else {
            // The ID does not exist, so add it
            val uuid = UUID.randomUUID().toString()
            val editor = sharedPreferences.edit()
            editor.putInt(uuid, id)
            editor.apply()
            Log.d("gabs", "onCreate: Added movie!")
        }
    }

    fun loadFavMoviesIds() {
        Log.d("gabs", "onCreate:Loaded movies!")
        val allEntries = sharedPreferences.all
        _favouriteIdList.value = allEntries.values.mapNotNull { it as? Int }
        Log.d("gabs", "loadFavMoviesIds: $_favouriteIdList")
        getFavouriteMovies(_favouriteIdList.value)
    }

    private fun clearSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private val _indexScreen: MutableState<Int> = mutableStateOf(0)
    val indexScreen = _indexScreen

    private val _isFavourite: MutableState<Boolean> = mutableStateOf(false)
    val isFavourite = _isFavourite

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading = _isLoading

    private val _movieList: MutableState<List<PopularMoviesResponse.Result>> =
        mutableStateOf(listOf())
    val movieList = _movieList

    private val _favouriteIdList: MutableState<List<Int>> =
        mutableStateOf(listOf())
    val favouriteIdList = _favouriteIdList

    private val _favouriteMovieList: MutableState<List<PopularMoviesResponse.Result>> =
        mutableStateOf(listOf())
    val favouriteMovieList = _favouriteMovieList

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

    fun showFavourite(isFavourite: Boolean) {
        _isFavourite.value = isFavourite
    }
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
            val response = RetrofitHelper.getRetrofit()
                .getPopularMovies(api_key = Constants.API_KEY)
            if (response.isSuccessful) {
                _movieList.value = response.body()!!.results
            }
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val encodedQuery = URLEncoder.encode(query, "UTF-8")
            val response =
                RetrofitHelper.getRetrofit().searchMovies(Constants.API_KEY, encodedQuery)
            if (response.isSuccessful) {
                Log.d("gabs", "searchMovie: success")
                _searchedMovies.value = response.body()!!.results
            } else Log.d("gabs", "searchMovie: error")
            _isLoading.value = false
        }
    }

    fun getFavouriteMovies(id: List<Int>) {
        viewModelScope.launch {
            _isLoading.value = true
            val tempList = mutableListOf<PopularMoviesResponse.Result>()
            id.forEach {
                Log.d("gabs", "movies ids sent out: $it")
                val response = RetrofitHelper.getRetrofit().searchMovieById(it, Constants.API_KEY)
                Log.d("gabs", "getFavouriteMovies: $response")
                if (response.isSuccessful) {
                    val movieItem: PopularMoviesResponse.Result = response.body()!!
                    tempList.add(movieItem)
                }
            }
            _favouriteMovieList.value = tempList
            _isLoading.value = false
        }
    }
}