package com.atruskova.itunesapitesttask.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.atruskova.indeedtestapp.data.network.models.Album
import java.util.*

@Entity(tableName = "Albums")
data class AlbumEntity (
    @PrimaryKey
    val ID : String,
    val WrapperType: String,
    val CollectionType: String,
    val ArtistID: Long,
    val AmgArtistId: Long,
    val ArtistName: String,
    val CollectionName: String,
    val CollectionCensoredName: String,
    val ArtistViewUrl: String,
    val CollectionViewUrl: String,
    val ArtWorkUrl60: String,
    val ArtWorkUrl100: String,
    val CollectionPrice: String,
    val CollectionExplictness: String,
    val TrackCount: Int,
    val Copyright: String,
    val Country: String,
    val Currency: String,
    val ReleaseDate: Date?,
    val PrimaryGenreName: String,
    var isFavourite: Boolean = false

) {
    companion object {
        fun from(dto: Album,
                 isFavorite: Boolean) : AlbumEntity {
            dto.let {
                return AlbumEntity(
                    it.ID,
                    it.wrapperType?:"",
                    it.collectionType?:"",
                    it.artistId?:0,
                    it.amgArtistId?:0,
                    it.artistName?:"",
                    it.collectionName?:"",
                    it.collectionCensoredName?:"",
                    it.artistViewUrl?:"",
                    it.collectionViewUrl?:"",
                    it.artworkUrl60?:"",
                    it.artworkUrl100?:"",
                    it.collectionPrice?:"",
                    it.collectionExplicitness?:"",
                    it.trackCount?:0,
                    it.copyright?:"",
                    it.country?:"",
                    it.currency?:"",
                    it.releaseDate,
                    it.primaryGenreName?:"",
                    isFavorite
                )
            }

        }
    }
}