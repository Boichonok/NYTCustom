package com.example.nytdevelopersmpapiclient.Model.Repository.OldRepository

import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.ArticleResponse
import io.reactivex.Completable
import io.reactivex.Flowable

interface NtArticleRepository {
    fun findMostViewedArticles(section: String, timePeriod: Int): Flowable<ArticleResponse>
    fun findMostSharedArticles(section:String, timePeriod: Int): Flowable<ArticleResponse>
    fun finMostEmailedArticles(section:String,timePeriod: Int): Flowable<ArticleResponse>
    fun saveArticle(articleMainInfo: ArticleMainInfo)

}