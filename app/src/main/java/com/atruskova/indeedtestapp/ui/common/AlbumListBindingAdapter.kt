package com.atruskova.itunesapitesttask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.databinding.AlbumListItemBinding
import com.atruskova.indeedtestapp.ui.common.AlbumBaseViewModel
import com.atruskova.indeedtestapp.ui.common.AlbumListBindingHandler
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity

class AlbumListBindingAdapter(
    private val viewModel: AlbumBaseViewModel
) : ListBindingAdapter<AlbumEntity, AlbumListItemBinding> (
    diffCallback = object : DiffUtil.ItemCallback<AlbumEntity> (){
        override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean {
            return oldItem.ID==newItem.ID
        }

        override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity): Boolean {
            return oldItem.equals(newItem)
        }
    }
){

    override fun createBinding(parent: ViewGroup): AlbumListItemBinding {
        return  DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.album_list_item,
                parent,
                false
            )
    }

    override fun bind(binding: AlbumListItemBinding, item: AlbumEntity) {
        binding.album = item
        binding.handler = AlbumListBindingHandler(viewModel)
    }
}