package com.atruskova.indeedtestapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.databinding.FragmentHomeBinding
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity
import com.atruskova.itunesapitesttask.ui.AlbumListBindingAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: AlbumListBindingAdapter
    private var searchHandler = Handler()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this.activity
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AlbumListBindingAdapter(homeViewModel)
        albums_list_rv.adapter = adapter
        album_list_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                searchHandler.removeCallbacksAndMessages(null)
                searchHandler.postDelayed({
                    homeViewModel.setSearchQuery(newText ?: "")
                }, 300)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.setSearchQuery(query ?: "")
                return true
            }
        }
        )
        subscribeAlbumList(homeViewModel.currentSearchList)
    }

    private fun subscribeAlbumList(liveData: LiveData<List<AlbumEntity>>) {
        liveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }
}