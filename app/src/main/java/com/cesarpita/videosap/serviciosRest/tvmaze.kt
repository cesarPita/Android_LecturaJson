package com.cesarpita.videosap.serviciosRest

import com.cesarpita.videosap.modelos.ContenerdorShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface tvmaze {
    @GET("search/shows?")
    fun listarPeliculas(@Query("q") filtro: String): Call<List<ContenerdorShow>>
}