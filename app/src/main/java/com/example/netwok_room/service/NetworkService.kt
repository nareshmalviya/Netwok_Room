package com.example.netwok_room.service

import com.example.netwok_room.model.Genres
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {

    @GET("discover/movie")
   suspend fun getMovies(
        @Query("api_key") apiKey: String?,
        @Query("page") page: Int?
    ): Response<Genres>
}