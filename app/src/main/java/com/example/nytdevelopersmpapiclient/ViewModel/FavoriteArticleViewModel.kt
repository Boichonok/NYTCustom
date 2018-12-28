package com.example.nytdevelopersmpapiclient.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp
import com.example.nytdevelopersmpapiclient.Model.Repository.ArticleNTRepository
import com.example.nytdevelopersmpapiclient.Model.Repository.FavoriteArticlesRepository
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class FavoriteArticleViewModel: ViewModel() {
    private  var favoriteArticlesRepository: FavoriteArticlesRepository = ArticleNTRepository(NTArticlesApp.getAppComponent().getContext())



    fun getAllFavoriteArticles(): Flowable<List<ArticleMainInfo>>//LiveData<List<ArticleMainInfo>>
    {
        return favoriteArticlesRepository.getAllFavoriteArticles()
    }

    fun getSomething(): String
    {
        return "Something"
    }

    fun deleteAllFavoriteArticles(): Completable
    {
        return Completable.fromAction({
            favoriteArticlesRepository.deleteAllFavoriteArticle()
        })


    }

    fun deleteFavoriteArticleById(id:Int): Completable
    {
          return Completable.fromAction({
              favoriteArticlesRepository.deleteFavoriteArticleById(id)
          })
    }

}