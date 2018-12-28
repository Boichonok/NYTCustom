package com.example.nytdevelopersmpapiclient.Model.Repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.DAO.ArticleMainInfoDAO
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Room.ArticleRoomDataBase
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.API.API
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.API.APIService
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.ArticleResponse
import io.reactivex.Completable
import io.reactivex.Flowable

class ArticleNTRepository : NtArticleRepository,FavoriteArticlesRepository {



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

    private  var articleMainInfoDAO: ArticleMainInfoDAO
    private  var newYourTimesAPI: API

    constructor(context: Context)
    {
        var dataBase = ArticleRoomDataBase.getDataBase(context)
        articleMainInfoDAO = dataBase!!.ArticleMainInfoDAO()

        newYourTimesAPI = APIService.getInstance()

    }

}