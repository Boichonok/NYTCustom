package com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity

import com.google.gson.annotations.SerializedName

data class MediaMetoData(@SerializedName("url") val url:String,
                         @SerializedName("width") val width: Int,
                         @SerializedName("height") val height: Int)