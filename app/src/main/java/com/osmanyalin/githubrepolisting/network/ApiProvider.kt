package com.osmanyalin.githubrepolisting.network

class ApiProvider(private val apiService: APIService) {

    suspend fun getUserRepos(username: String) = apiService.getUserRepos(username)
}