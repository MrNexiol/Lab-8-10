package com.prograils.lab8_10

import com.prograils.lab8_10.api.Repository
import com.prograils.lab8_10.api.TMDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppContainer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieService: TMDbService = retrofit.create(TMDbService::class.java)
    val repository = Repository(movieService)
}