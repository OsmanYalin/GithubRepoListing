package com.osmanyalin.githubrepolisting.network

import com.osmanyalin.githubrepolisting.model.RepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("page") page: String): Response<List<RepoModel>>
}
