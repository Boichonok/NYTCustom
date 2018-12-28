package com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity

import com.google.gson.annotations.SerializedName

data class Media(@SerializedName("media-metadata") val mediaMetoData: List<MediaMetoData>)