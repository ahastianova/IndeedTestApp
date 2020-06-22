package com.atruskova.indeedtestapp.ui.home

import androidx.lifecycle.*
import com.atruskova.indeedtestapp.data.repository.AlbumsRepository
import com.atruskova.indeedtestapp.ui.common.AlbumBaseViewModel
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val albumsRepository: AlbumsRepository
) : AlbumBaseViewModel(albumsRepository) {

    var searchQuery: MutableLiveData<String> = MutableLiveData()
    var emptyQueryFlag: LiveData<Boolean> = Transformations.map(searchQuery) {it.isEmpty()}

    private var _loadingFlag: MutableLiveData<Boolean> = MutableLiveData()
    var loadingFlag: LiveData<Boolean> = _loadingFlag

    private val _currentSearchList: MutableLiveData<List<AlbumEntity>> = MutableLiveData()
    val currentSearchList: LiveData<List<AlbumEntity>> = _currentSearchList


    private val _emptyResultList: MutableLiveData<Boolean> = MutableLiveData()
    val emptyResultList: LiveData<Boolean> = _emptyResultList

    init {
        searchQuery.postValue("")
    }

    fun setSearchQuery(query: String) {
        _emptyResultList.postValue(false)
        searchQuery.postValue(query)
        if (query.isNotBlank()) {
            updateFromServer(query)
        } else {
            _currentSearchList.postValue(emptyList())
        }
    }


    private fun updateFromServer(query: String)  {
        viewModelScope.launch {
            _loadingFlag.postValue(true)
            val result = albumsRepository.getAlbumsFromServer(query)
            if (result.isEmpty())
                _emptyResultList.postValue(true)
            _currentSearchList.postValue(result)
            _loadingFlag.postValue(false)
        }
    }
}