package com.atruskova.indeedtestapp.di

import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.data.database.AppDatabase
import com.atruskova.indeedtestapp.data.network.NetworkManager
import com.atruskova.indeedtestapp.data.repository.AlbumsRepository
import com.atruskova.indeedtestapp.data.repository.RegisterRepository
import com.atruskova.indeedtestapp.helper.utils.CryptographyManager
import com.atruskova.indeedtestapp.ui.favorites.FavoritesViewModel
import com.atruskova.indeedtestapp.ui.home.HomeViewModel
import com.atruskova.indeedtestapp.ui.register.RegisterViewModel
import com.atruskova.itunesapitesttask.api.Api
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DIManager {

    fun getModules() =
        listOf(
            appModule,
            viewModelModule,
            repositoryModule,
            managerModule,
            roomModule
        )

    private val appModule = module {

        single {
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
            httpClientBuilder.build()
        }

        factory {
            Retrofit.Builder()
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(androidContext().getString(R.string.base_url))
                .build()
                .create(Api::class.java)
        }
    }

    private val managerModule = module {
        single { NetworkManager(get()) }
        single { CryptographyManager() }
    }

    private val repositoryModule = module {
        single { RegisterRepository(androidContext(), get()) }
        single { AlbumsRepository(get(), get()) }
    }

    private val viewModelModule = module {
        viewModel {
            RegisterViewModel(get())
        }
        viewModel { HomeViewModel(get()) }
        viewModel { FavoritesViewModel(get()) }
    }

    private val roomModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single(createdAtStart = false) { get<AppDatabase>().getAlbumDao() }
    }
}