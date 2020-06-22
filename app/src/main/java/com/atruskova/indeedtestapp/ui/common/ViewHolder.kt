package com.atruskova.itunesapitesttask.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder<out T : ViewDataBinding> constructor (val binding: T)
    : RecyclerView.ViewHolder(binding.root)

