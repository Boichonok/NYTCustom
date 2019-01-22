package com.example.nytdevelopersmpapiclient.Model.Repository.OldRepository

import android.content.Context
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.DAO.ArticleMainInfoDAO
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.API.API
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.API.APIService
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.ArticleResponse
import io.reactivex.Flowable
import javax.inject.Inject

class ArticleNTRepository : NtArticleRepository, FavoriteArticlesRepository {

    @Inject
    protected lateinit var articleMainInfoDAO: ArticleMainInfoDAO

    @Inject
    protected lateinit var newYourTimesAPI: API

    constructor(context: Context)
    {
        //NTArticlesApp.getMostPopularAPIComponent().inject(this)
    }


    override fun saveArticle(articleMainInfo: ArticleMainInfo) {

        val url = getItemUrlByTitle(articleMainInfo.title)
        if(url == null) {
            articleMainInfoDAO.insertArticleMainInfo(articleMainInfo)
        }
        else
        {
            updateArticleMainInfo(articleMainInfo)
        }

    }

    override fun updateArticleMainInfo(articleMainInfo: ArticleMainInfo) {
        articleMainInfoDAO.updateArticleMainInfo(articleMainInfo)
    }

    override fun getItemUrlByTitle(title:String): String {
        return articleMainInfoDAO.getItemUrlByTitle(title)
    }


    override fun findMostViewedArticles(section: String, timePeriod: Int): Flowable<ArticleResponse> {
        return newYourTimesAPI.getMostViewedArticles(section,timePeriod,APIService.API_KEY)
    }

    override fun findMostSharedArticles(section: String, timePeriod: Int): Flowable<ArticleResponse> {
        return newYourTimesAPI.getMostSharedArticles(section,timePeriod,APIService.API_KEY)
    }

    override fun finMostEmailedArticles(section: String, timePeriod: Int): Flowable<ArticleResponse> {
        return newYourTimesAPI.getMostEmailedArticles(section,timePeriod,APIService.API_KEY)
    }


    override fun deleteAllFavoriteArticle() {
        articleMainInfoDAO.deleteAllArticles()
    }

    override fun deleteFavoriteArticleById(id: Int) {
        articleMainInfoDAO.deleteArticleByID(id)
    }

    override fun getAllFavoriteArticles():Flowable<List<ArticleMainInfo>> { //LiveData<List<ArticleMainInfo>> {
        return  articleMainInfoDAO.getAllArticles()
    }

}