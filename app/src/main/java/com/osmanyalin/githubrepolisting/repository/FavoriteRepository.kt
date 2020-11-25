package com.osmanyalin.githubrepolisting.repository

import com.osmanyalin.githubrepolisting.db.AppDatabase
import com.osmanyalin.githubrepolisting.db.entity.FavoriteDBModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FavoriteRepository @Inject constructor(appDatabase: AppDatabase): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private var favoritesDao = appDatabase.favoritesDao
    var favoriteList: Flow<List<FavoriteDBModel>> = favoritesDao.getFavorites()

    fun insertFavorite(favorite: FavoriteDBModel) {
        launch {
            withContext(Dispatchers.IO) {
                favoritesDao.insertFavorite(favorite)
            }
        }
    }

    fun deleteFavorite(id: String) {
        launch {
            withContext(Dispatchers.IO) {
                favoritesDao.deleteFavorite(id)
            }
        }
    }
}