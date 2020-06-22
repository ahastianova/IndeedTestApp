package com.atruskova.indeedtestapp.ui.favorites

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.atruskova.indeedtestapp.data.repository.AlbumsRepository
import com.atruskova.indeedtestapp.ui.common.AlbumBaseViewModel
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity

class FavoritesViewModel (
    private val albumsRepository: AlbumsRepository
) : AlbumBaseViewModel(albumsRepository) {
    val currentList: LiveData<List<AlbumEntity>> = albumsRepository.getSavedAlbums()

    val isEmptyListVisible: LiveData<Int> = Transformations.map(currentList) {
        if (it.isEmpty())
            View.VISIBLE
        else
            View.GONE
    }
}