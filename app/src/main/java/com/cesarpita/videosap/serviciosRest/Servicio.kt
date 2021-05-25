package com.cesarpita.videosap.serviciosRest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Servicio {
    lateinit var retrofit: Retrofit
    val BASE_URL = "http://api.tvmaze.com/"
    constructor(){
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getTvmaze() : tvmaze{
        return retrofit.create(tvmaze::class.java)
    }
}