package com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity

import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName
import java.util.*


data class Article (@SerializedName("url") var url: String,
                    @SerializedName("count_type") var countType: String,
                    @SerializedName("section") var section: String,
                    @SerializedName("source") var source: String,
                    @SerializedName("title") var title: String,
                    @SerializedName("abstract") var abstractText: String,
                    @SerializedName("byline") @NonNull var byLine: String,
                    @SerializedName("published_date") var publishedDate: String,
                    @SerializedName("media") @NonNull var media: MediaList)
