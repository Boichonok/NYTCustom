package com.example.nytdevelopersmpapiclient

import android.content.Context
import android.widget.Toast
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Article
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.Media
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.MediaList
import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.MediaMetoData

class Utils {
    companion object {
        fun ShowToast(message: String,context: Context) {
            Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        }

        fun IsValidArticlesList(list: ArrayList<Article>): ArrayList<Article>
        {
            var nonNullList = ArrayList<Article>()
            nonNullList.addAll(list.filterNotNull())

            var size = nonNullList.size
            for (i in size - 1 downTo 0 step 1)
            {
                if(nonNullList[i].byLine == null)
                {
                    nonNullList[i].byLine = "No author"
                }
                if (nonNullList[i].media == null)
                {
                    val mediaList = MediaList()
                    val mediaMetoData = ArrayList<MediaMetoData>()
                    mediaMetoData.add(MediaMetoData("http",433,320))
                    mediaList.add(Media(mediaMetoData))
                    nonNullList[i].media = mediaList
                }
                if (nonNullList[i].abstractText == null)
                {
                    nonNullList[i].abstractText = "No abstract"
                }
                if(nonNullList[i].countType == null)
                {
                    nonNullList[i].countType = "No count type"
                }
                if(nonNullList[i].publishedDate == null)
                {
                    nonNullList[i].publishedDate = "No published date"
                }
                if(nonNullList[i].section == null)
                {
                    nonNullList[i].section = "No section"
                }
                if(nonNullList[i].source == null)
                {
                    nonNullList[i].source = "No source"
                }
                if(nonNullList[i].title == null)
                {
                    nonNullList[i].title = "No title"
                }
                if(nonNullList[i].url == null)
                {
                    nonNullList[i].url = "No url"
                }
            }
            return nonNullList as ArrayList<Article>
        }
    }
}