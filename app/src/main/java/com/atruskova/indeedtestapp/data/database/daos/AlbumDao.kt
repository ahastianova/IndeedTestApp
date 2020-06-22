package com.atruskova.indeedtestapp.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity

@Dao
interface AlbumDao {

    @Query("SELECT * FROM Albums")
    fun getAll(): LiveData<List<AlbumEntity>>

    @Query("SELECT ID FROM Albums WHERE ID=:id")
    suspend fun isSaved(id: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumEntity)

    @Delete
    suspend fun delete(album: AlbumEntity)
}