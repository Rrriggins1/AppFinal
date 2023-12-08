package com.example.appfinal.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MBMovie(
    val title: String,
    val poster: String,
    val description: String,
    @SerialName ("release_date")
    val releaseDate: String,
    @SerialName ("content_rating")
    val contentRating: String,
    @SerialName ("review_score")
    val reviewScore: String,
    @SerialName ("big_image")
    val bigImage: String,
    val length: String
)
