package com.atruskova.indeedtestapp.data.database

import android.content.Context
import androidx.room.*
import com.atruskova.indeedtestapp.data.database.AppDatabase.Companion.DB_VERSION
import com.atruskova.indeedtestapp.data.database.daos.AlbumDao
import com.atruskova.itunesapitesttask.data.database.DatabaseTypeConverter
import com.atruskova.itunesapitesttask.data.entities.AlbumEntity

@Database(
    entities = [
        AlbumEntity::class
    ],
    exportSchema = false,
    version = DB_VERSION
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAlbumDao (): AlbumDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "IndeedTestApp-db"
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, DB_NAME
            ).build()
    }
}