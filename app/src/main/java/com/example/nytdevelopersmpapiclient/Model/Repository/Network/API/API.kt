package com.example.nytdevelopersmpapiclient.Model.Repository.Network.API

import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.ArticleResponse
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("mostviewed/{section}/{time-period}.json")
    fun getMostViewedArticles(@Path("section") section: String,
                              @Path("time-period") timePeriod: Int,
                              @Query("api_key") apiKey: String): Flowable<ArticleResponse>//Call<ArticleResponse>

    @GET("mostshared/{section}/{time-period}.json")
    fun getMostSharedArticles(@Path("section") section: String,
                              @Path("time-period") timePeriod: Int,
                              @Query("api_key") apiKey: String): Flowable<ArticleResponse>//Call<ArticleResponse>

    @GET("mostemailed/{section}/{time-period}.json")
    fun getMostEmailedArticles(@Path("section") section: String,
                              @Path("time-period") timePeriod: Int,
                              @Query("api_key") apiKey: String): Flowable<ArticleResponse>//Call<ArticleResponse>
}