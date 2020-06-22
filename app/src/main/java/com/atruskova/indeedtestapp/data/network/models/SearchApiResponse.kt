package com.atruskova.itunesapitesttask.api

import com.atruskova.indeedtestapp.data.network.models.Album
import com.google.gson.annotations.SerializedName

data class SearchApiResponse (
    @SerializedName("resultCount")
    var count: Int =0,
    @SerializedName("results")
    var items: List<Album>?
)