package com.prograils.lab8_10.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val service: TMDbService) {
    fun getMoviesByTitle(query: String): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        service.getMoviesByTitle(query = query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.body() != null) {
                    data.value = response.body()!!.results
                } else {
                    data.value = null
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun discoverMovies(year: Int?, actorIds: String?): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        service.discoverMovies(year = year, withCast = actorIds).enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.body() != null) {
                    data.value = response.body()!!.results
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun getActors(query: String): LiveData<List<Actor>> {
        val data = MutableLiveData<List<Actor>>()
        service.getActors(query = query).enqueue(object : Callback<ActorResponse> {
            override fun onResponse(call: Call<ActorResponse>, response: Response<ActorResponse>) {
                if (response.body() != null) {
                    data.value = response.body()!!.results
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<ActorResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}