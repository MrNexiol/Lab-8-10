package com.prograils.lab8_10.api

import com.google.gson.annotations.SerializedName

data class Movie(
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val overview: String
)
