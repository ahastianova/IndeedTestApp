package com.atruskova.indeedtestapp.ui.common

import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.viewModelScope
import com.atruskova.indeedtestapp.helper.extensions.changeFavoriteState
import com.atruskova.indeedtestapp.ui.home.HomeViewModel
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity
import kotlinx.coroutines.launch

class AlbumListBindingHandler(
    private val viewModel: AlbumBaseViewModel
) {
    fun onClick(view: View, album: AlbumEntity) {
        viewModel.viewModelScope.launch {
            with(album) {
                val isFavorite = viewModel.isFavorite(this)
                if (isFavorite) {
                    viewModel.delete(this)
                } else {
                    viewModel.saveLocal(this)
                }
                (view as ImageButton).changeFavoriteState(!isFavorite)
            }
        }


    }
}