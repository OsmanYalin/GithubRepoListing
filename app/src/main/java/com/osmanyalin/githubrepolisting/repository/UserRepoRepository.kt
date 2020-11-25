package com.osmanyalin.githubrepolisting.repository

import com.osmanyalin.githubrepolisting.network.ApiProvider
import javax.inject.Inject

class UserRepoRepository @Inject constructor(private val apiProvider: ApiProvider) {

    suspend fun getUserRepos(username: String, page: String) = apiProvider.getUserRepos(username, page).body()
}