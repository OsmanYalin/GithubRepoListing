package com.osmanyalin.githubrepolisting.repository

import com.osmanyalin.githubrepolisting.network.ApiProvider
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiProvider: ApiProvider) {

    suspend fun getUserRepos(username: String) = apiProvider.getUserRepos(username).body()
}