package com.example.appfinal.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "https://kareemy.github.io"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MBApiService {
    @GET("MovieBuffs/movies.json")
    suspend fun getMovies(): List<MBMovie>

}

object MBApi {
    val retrofitService : MBApiService by lazy {
        retrofit.create(MBApiService::class.java)
    }
}