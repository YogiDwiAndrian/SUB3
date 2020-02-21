package com.yogi.moviecatalog.Api

import com.yogi.moviecatalog.BuildConfig
import com.yogi.moviecatalog.Models.ObjectResultMovie
import com.yogi.moviecatalog.Models.ObjectResultTVShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTheMovieDb {

    @GET("movie/now_playing")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = BuildConfig.LANGUAGE
    ): Observable<ObjectResultMovie>

    @GET("tv/on_the_air")
    fun getPopularTVShow(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = BuildConfig.LANGUAGE,
        @Query("page") page: Int
    ): Observable<ObjectResultTVShow>
}