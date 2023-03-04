package com.example.moviebrowser.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel: ViewModel() {
    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    fun onIndexChange(index: Int) {
        _index.value = index
    }
}