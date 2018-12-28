package com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.DAO.ArticleMainInfoDAO
import com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity.ArticleMainInfo


@Database(entities = arrayOf(ArticleMainInfo::class),version = 1)
abstract class ArticleRoomDataBase: RoomDatabase() {
    abstract fun ArticleMainInfoDAO(): ArticleMainInfoDAO

    companion object {
        @Volatile var Instance: ArticleRoomDataBase? = null

        fun getDataBase ( context: Context): ArticleRoomDataBase?
        {
            if(Instance == null)
            {
                synchronized( ArticleRoomDataBase::class.java) {
                    if(Instance == null)
                    {
                        Instance = Room.databaseBuilder(context.applicationContext,
                            ArticleRoomDataBase::class.java,
                            "article_database").build()
                    }
                }
            }
            return  Instance!!
        }
    }

}