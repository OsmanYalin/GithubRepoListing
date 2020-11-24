package com.osmanyalin.githubrepolisting.network

import com.osmanyalin.githubrepolisting.model.RepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

  @GET("users/{username}/repos")
  suspend fun getUserRepos(
      @Path("username") username: String): Response<List<RepoModel>>
}
