package com.atruskova.indeedtestapp.data.repository

import androidx.lifecycle.LiveData
import com.atruskova.indeedtestapp.data.database.daos.AlbumDao
import com.atruskova.indeedtestapp.data.network.NetworkManager
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity

class AlbumsRepository(
    private val networkManager: NetworkManager,
    private val albumDao: AlbumDao
) {

    suspend fun getAlbumsFromServer(query: String) : List<AlbumEntity> {
        val list = mutableListOf<AlbumEntity>()
        networkManager.getAlbums(query)?.items?.let {
            list.addAll(it.map { album->
                AlbumEntity.from(album, isSavedLocal(album.ID))
            })
        }
        return list
    }

    suspend fun saveLocal(albumEntity: AlbumEntity) {
        albumDao.insert(albumEntity.apply {
            this.isFavourite = true
        })
    }

    suspend fun deleteLocal(albumEntity: AlbumEntity) {
        albumDao.delete(albumEntity)
    }

    suspend fun isSavedLocal(albumID: String) : Boolean {
        if (!albumDao.isSaved(albumID).isNullOrBlank())
            return true
        return false
    }

    fun getSavedAlbums() : LiveData<List<AlbumEntity>> = albumDao.getAll()
}