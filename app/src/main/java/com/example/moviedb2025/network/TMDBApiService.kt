package com.example.moviedb2025.network

import com.example.moviedb2025.models.ReviewResponse
import com.example.moviedb2025.models.VideoResponse
import com.example.moviedb2025.models.NowPlayingResponse
import com.example.moviedb2025.models.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {
    //For getting the first page of reviews for a given movie
    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ReviewResponse
    //For getting the list of videos related to the movie (usually trailers)
    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): VideoResponse
    //Getting a list of movies currently playing
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): NowPlayingResponse
    //Getting a list of top rated movies
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): NowPlayingResponse //Reusing the same response model here, since it's compatible
    //Get details for specific movie
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): Movie

}
