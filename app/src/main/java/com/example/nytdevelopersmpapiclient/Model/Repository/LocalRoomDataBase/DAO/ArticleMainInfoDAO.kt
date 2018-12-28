package com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.DAO

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ArticleMainInfoDAO {
    @Insert
    fun insertArticleMainInfo(articleMainInfo: ArticleMainInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticleMainInfo(articleMainInfo: ArticleMainInfo)

    @Query("DELETE FROM article_main_info_table")
    fun deleteAllArticles()

    @Query("DELETE FROM article_main_info_table WHERE id == :id")
    fun deleteArticleByID(id:Int)

    @Query("SELECT * FROM article_main_info_table")
    fun getAllArticles():Flowable<List<ArticleMainInfo>> //LiveData<List<ArticleMainInfo>>

    @Query("SELECT url FROM article_main_info_table WHERE title == :title")
    fun getItemUrlByTitle(title:String): String

}