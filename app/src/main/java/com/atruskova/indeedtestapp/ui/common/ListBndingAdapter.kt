package com.atruskova.itunesapitesttask.ui

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class ListBindingAdapter<T ,V : ViewDataBinding> (
    diffCallback: DiffUtil.ItemCallback<T>
): ListAdapter<T, ViewHolder<V>>(diffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        var binding = createBinding(parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }
    abstract fun createBinding(parent: ViewGroup): V

    abstract fun bind(binding: V, item: T)
}