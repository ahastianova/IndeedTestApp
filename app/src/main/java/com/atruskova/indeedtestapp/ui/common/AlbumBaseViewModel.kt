package com.atruskova.indeedtestapp.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atruskova.indeedtestapp.data.repository.AlbumsRepository
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity
import kotlinx.coroutines.launch

abstract class AlbumBaseViewModel(
    private val albumsRepository: AlbumsRepository
) : ViewModel() {
    fun saveLocal(albumEntity: AlbumEntity) {
        viewModelScope.launch {
            albumsRepository.saveLocal(albumEntity)
        }
    }

    fun delete(albumEntity: AlbumEntity) {
        viewModelScope.launch {
            albumsRepository.deleteLocal(albumEntity)
        }
    }

    suspend fun isFavorite(albumEntity: AlbumEntity) : Boolean {
        return albumsRepository.isSavedLocal(albumEntity.ID)
    }
}