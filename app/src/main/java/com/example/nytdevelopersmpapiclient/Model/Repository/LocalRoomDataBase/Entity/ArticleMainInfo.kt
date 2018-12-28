package com.example.nytdevelopersmpapiclient.Model.Repository.LocalRoomDataBase.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull



@Entity(tableName = "article_main_info_table")
data class ArticleMainInfo (@NonNull
                            @ColumnInfo(name = "url")
                            var url:String = "",
                            @NonNull
                            @ColumnInfo(name = "url_picture")
                            var url_picture:String = "",
                            @ColumnInfo(name = "count_type")
                            var sortType: String = "",
                            @NonNull
                            @ColumnInfo(name = "section")
                            var section: String = "",
                            @NonNull
                            @ColumnInfo(name = "by_line")
                            var byline: String = "",
                            @NonNull
                            @ColumnInfo(name = "abstract_")
                            var abstract_: String = "",
                            @NonNull
                            @ColumnInfo(name = "published_date")
                            var publishedDate: String = "",
                            @NonNull
                            @ColumnInfo(name = "source")
                            var source: String = "",
                            @NonNull
                            @ColumnInfo(name = "title")
                            var title: String = "")
{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}