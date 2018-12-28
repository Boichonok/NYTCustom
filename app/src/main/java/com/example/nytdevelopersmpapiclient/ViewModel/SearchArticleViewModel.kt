package com.example.nytdevelopersmpapiclient.ViewModel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.nytdevelopersmpapiclient.DaggerDI.NTArticlesApp

import com.example.nytdevelopersmpapiclient.Model.Repository.ArticleNTRepository
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.Model.Repository.NtArticleRepository
import com.example.nytdevelopersmpapiclient.Utils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins


class SearchArticleViewModel: ViewModel() {

    enum class Social{
        MOST_SHARED,
        MOST_EMAILED,
        MOST_VIEWED,
        NON
    }

    private var  ntArticleRepository: NtArticleRepository =  ArticleNTRepository(NTArticlesApp.getAppComponent().getContext())

    private var requestBuilder: BuildRequestForArticle = BuildRequestForArticle()

    fun requestBuilder(): BuildRequestForArticle
    {
        return requestBuilder
    }

     fun findMostViewedArticles(section:String, timePeriod:Int) : Flowable<List<Article>>
    {
        return ntArticleRepository.findMostViewedArticles(section,timePeriod).map {
            return@map Utils.IsValidArticlesList(it.articles)
        }
    }

    private fun findMostEmailedArticles(section: String,timePeriod: Int): Flowable<List<Article>>
    {
        return ntArticleRepository.finMostEmailedArticles(section,timePeriod).map {
            return@map Utils.IsValidArticlesList(it.articles)
        }
    }

    private fun findMostSharedArticles(section: String,timePeriod: Int): Flowable<List<Article>>
    {
        return ntArticleRepository.findMostSharedArticles(section,timePeriod).map {
            return@map Utils.IsValidArticlesList(it.articles)
        }
    }

    fun saveArticle(articleMainInfo: ArticleMainInfo): Completable
    {
        return Completable.fromAction({
            ntArticleRepository.saveArticle(articleMainInfo)
        })
    }

    class BuildRequestForArticle()
    {
        private var section:String = ""
        private var timePeriod:Int = 0
        private var social: Social = Social.NON

        fun setSection(section: String): BuildRequestForArticle
        {
            this.section = section
            return this
        }

        fun setTimePeriod(timePeriod: Int): BuildRequestForArticle
        {
            this.timePeriod = timePeriod
            return this
        }

        fun setSocial(social: Social): BuildRequestForArticle
        {
            this.social = social
            return this
        }

        fun build(): Flowable<List<Article>>
        {
            Log.d("ViewModelSearch", "Builder: section ${this.section} timePeriod: ${this.timePeriod} social: ${social.toString()}")
            when(this.social)
            {
                Social.MOST_EMAILED -> return SearchArticleViewModel().findMostEmailedArticles(this.section,this.timePeriod)
                Social.MOST_VIEWED -> return SearchArticleViewModel().findMostViewedArticles(this.section,this.timePeriod)
                else -> return SearchArticleViewModel().findMostSharedArticles(this.section,this.timePeriod)
            }
        }
    }
}