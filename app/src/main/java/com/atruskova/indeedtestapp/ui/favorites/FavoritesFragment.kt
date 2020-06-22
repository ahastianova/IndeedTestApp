package com.atruskova.indeedtestapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.databinding.FragmentFavoritesBinding
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity
import com.atruskova.itunesapitesttask.ui.AlbumListBindingAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private lateinit var adapter: AlbumListBindingAdapter
    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favorites, container, false
        )
        binding.viewmodel = favoritesViewModel
        binding.lifecycleOwner = this.activity
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AlbumListBindingAdapter(favoritesViewModel)
        albums_list_rv.adapter = adapter
        subscribeAlbumList(favoritesViewModel.currentList)
    }

    private fun subscribeAlbumList(liveData: LiveData<List<AlbumEntity>>) {
        liveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }
}