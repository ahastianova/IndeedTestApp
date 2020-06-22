package com.atruskova.indeedtestapp.data.network.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

data class Album (
    @SerializedName("collectionId")
    val ID : String,
    @SerializedName("wrapperType")
    val wrapperType: String?,
    @SerializedName("collectionType")
    val collectionType: String?,
    @SerializedName("artistId")
    val artistId: Long?,
    @SerializedName("amgArtistId")
    val amgArtistId: Long?,
    @SerializedName("artistName")
    val artistName: String?,
    @SerializedName("collectionName")
    val collectionName: String?,
    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String?,
    @SerializedName("artistViewUrl")
    val artistViewUrl: String?,
    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String?,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String?,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String?,
    @SerializedName("collectionPrice")
    val collectionPrice: String?,
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String?,
    @SerializedName("trackCount")
    val trackCount: Int?,
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("releaseDate")
    val releaseDate: Date?,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String?
)