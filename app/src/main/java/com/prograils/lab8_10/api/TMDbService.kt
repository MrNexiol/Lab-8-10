package com.prograils.lab8_10.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "069476b6f6d27163bdcf0eb3c19d181b"

interface TMDbService {
    @GET("search/movie")
    fun getMoviesByTitle(@Query("api_key") key: String = API_KEY,
                         @Query("query") query: String
    ): Call<MovieResponse>

    @GET("discover/movie")
    fun discoverMovies(@Query("api_key") key: String = API_KEY,
                       @Query("with_cast") withCast: String? = null,
                       @Query("year") year: Int? = null,
                       @Query("sort_by") sortBy: String = "release_date.desc"
    ): Call<MovieResponse>

    @GET("search/person")
    fun getActors(@Query("api_key") key: String = API_KEY,
                  @Query("query") query: String
    ): Call<ActorResponse>
}