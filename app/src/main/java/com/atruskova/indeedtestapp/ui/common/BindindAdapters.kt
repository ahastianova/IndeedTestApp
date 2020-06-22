package com.atruskova.indeedtestapp.ui.common

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.atruskova.indeedtestapp.Application
import com.atruskova.indeedtestapp.helper.extensions.changeFavoriteState
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter("getImage")
    @JvmStatic
    fun getImage(imageView: ImageView, url : String?) {
        Glide.with(Application.appContext).load(url).centerCrop().into(imageView)
    }

    @BindingAdapter("getFavoriteImageState")
    @JvmStatic
    fun getFavoriteImageState(imageView: ImageButton, isFavorite: Boolean) {
       imageView.changeFavoriteState(isFavorite)
    }

}