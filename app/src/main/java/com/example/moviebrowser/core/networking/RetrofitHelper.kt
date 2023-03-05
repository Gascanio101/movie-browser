package com.example.moviebrowser.core.networking

import com.example.moviebrowser.core.utils.Constants.Companion.BASE_URL
import com.example.moviebrowser.moviescreen.data.networking.MoviesClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {
    fun getRetrofit(): MoviesClient {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MoviesClient::class.java)
    }
}