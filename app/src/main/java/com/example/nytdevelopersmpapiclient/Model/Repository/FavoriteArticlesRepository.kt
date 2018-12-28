package com.example.nytdevelopersmpapiclient.Model.Repository

import android.arch.lifecycle.LiveData
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import io.reactivex.Completable
import io.reactivex.Flowable

interface FavoriteArticlesRepository {
    fun deleteAllFavoriteArticle()
    fun deleteFavoriteArticleById(id:Int)
    fun getAllFavoriteArticles(): Flowable<List<ArticleMainInfo>>//LiveData<List<ArticleMainInfo>>
    fun updateArticleMainInfo(articleMainInfo: ArticleMainInfo)
    fun getItemUrlByTitle(title:String): String

}