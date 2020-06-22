package com.atruskova.indeedtestapp.data.network

import com.atruskova.itunesapitesttask.api.Api
import com.atruskova.itunesapitesttask.api.SearchApiResponse
import org.koin.core.KoinComponent

class NetworkManager(
    private val api: Api
) : KoinComponent {

    suspend fun getAlbums(query: String): SearchApiResponse? {
        return api.search(query).body()
    }

}