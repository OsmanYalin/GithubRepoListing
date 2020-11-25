package com.osmanyalin.githubrepolisting.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.osmanyalin.githubrepolisting.db.dao.FavoritesDao
import com.osmanyalin.githubrepolisting.db.entity.FavoriteDBModel

@Database(entities = [FavoriteDBModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val favoritesDao: FavoritesDao

    companion object {
        const val DB_NAME = "github_repo_listing"
        const val TABLE_FAVORITES = "favorites"
    }
}
