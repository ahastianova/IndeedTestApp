package com.atruskova.itunesapitesttask.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @Headers("Content-Type: application/json")
    @GET("/search?entity=album")
    suspend fun search(@Query("term") query: String) : Response<SearchApiResponse>
}