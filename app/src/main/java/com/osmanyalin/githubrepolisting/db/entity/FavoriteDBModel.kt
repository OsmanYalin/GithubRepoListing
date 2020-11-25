package com.osmanyalin.githubrepolisting.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.osmanyalin.githubrepolisting.db.AppDatabase

@Entity(tableName = AppDatabase.TABLE_FAVORITES)
data class FavoriteDBModel(
    @PrimaryKey
    val _id: String,
    val repoName: String,
    val userName: String,
    val userAvatar: String,
    val openIssuesCount: String,
    val starCount: String)