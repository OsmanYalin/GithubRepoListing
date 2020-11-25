package com.osmanyalin.githubrepolisting.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osmanyalin.githubrepolisting.db.entity.FavoriteDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * from favorites")
    fun getFavorites() : Flow<List<FavoriteDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteDBModel)

    @Query("DELETE FROM favorites WHERE _id=:id")
    fun deleteFavorite(id: String)
}