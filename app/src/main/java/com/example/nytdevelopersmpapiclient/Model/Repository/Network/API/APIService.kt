package com.example.nytdevelopersmpapiclient.Model.Repository.Network.API

import com.example.nytdevelopersmpapiclient.Model.Repository.Network.Entity.MediaList

import com.google.gson.GsonBuilder

import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializer

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type


class APIService {



    companion object
    {
        val API_KEY: String = "0b0ee191bbb7464f8afd47d45810843c"

        private val API_URL: String = "https://api.nytimes.com/svc/mostpopular/v2/"

        private var mInstance: API? = null

        fun getInstance(): API
        {

            if(mInstance == null)
            {
                synchronized(APIService::class.java)
                {
                    if(mInstance == null)
                        mInstance = getRetrofit().create(API::class.java)
                }
            }
            return mInstance!!;
        }

        private fun getRetrofit() : Retrofit
        {
            return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson("yyyy-MM-dd")))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }

        private fun getGson(dateFormatString: String): Gson {
            return GsonBuilder().setDateFormat(dateFormatString)
                .registerTypeAdapter(MediaList::class.java, object : JsonDeserializer<MediaList> {
                    @Throws(JsonParseException::class)
                    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): MediaList? {
                        if (json.isJsonArray) {
                            val gson = Gson()
                            return gson.fromJson<MediaList>(json, MediaList::class.java)
                        }

                        return null
                    }
                }).create()
        }
    }



    private  constructor();






}