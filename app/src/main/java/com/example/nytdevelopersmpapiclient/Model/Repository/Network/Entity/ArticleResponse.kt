package com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity

import com.google.gson.annotations.SerializedName

data class ArticleResponse(@SerializedName("results") val articles: ArrayList<Article>)