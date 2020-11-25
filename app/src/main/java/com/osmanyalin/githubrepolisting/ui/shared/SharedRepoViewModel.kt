package com.osmanyalin.githubrepolisting.ui.shared

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.osmanyalin.githubrepolisting.base.BaseViewModel
import com.osmanyalin.githubrepolisting.db.entity.FavoriteDBModel
import com.osmanyalin.githubrepolisting.model.RepoModel
import com.osmanyalin.githubrepolisting.network.Resource
import com.osmanyalin.githubrepolisting.repository.FavoriteRepository
import com.osmanyalin.githubrepolisting.repository.UserRepoRepository
import kotlinx.coroutines.Dispatchers

class SharedRepoViewModel @ViewModelInject constructor(
    private val repoRepository: UserRepoRepository,
    private val favoriteRepository: FavoriteRepository,
    application: Application) : BaseViewModel(application) {

    private var listRepositories = mutableListOf<RepoModel>()
    val repoItem = MutableLiveData<RepoModel>()

    val allFavorites: LiveData<List<FavoriteDBModel>> = favoriteRepository.favoriteList.asLiveData()

    fun getUserRepos(username: String, page: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val userRepos = repoRepository.getUserRepos(username, page)

            markFavorites(userRepos)
            listRepositories.plusAssign(userRepos as MutableList<RepoModel>)
            emit(Resource.success(data = userRepos))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchRepoDetail(repoId: Int) {
        repoItem.value = listRepositories.single { it.id == repoId }
    }

    fun addFavorite(repoModel: RepoModel) {
        favoriteRepository.insertFavorite(
            FavoriteDBModel(
                repoModel.id,
                repoModel.name,
                repoModel.owner.login,
                repoModel.owner.avatar_url,
                repoModel.open_issues_count,
                repoModel.stargazers_count
            )
        )

        repoModel.isFavorite = true
        repoItem.value = repoModel
    }

    fun removeFavorite(repoModel: RepoModel) {
        favoriteRepository.deleteFavorite(repoModel.id)

        repoModel.isFavorite = false
        repoItem.value = repoModel
    }

    private fun markFavorites(userRepos: List<RepoModel>?) {
        userRepos?.let {
            for(repo in userRepos) {
                if(allFavorites.value?.find { it._id == repo.id } != null) {
                    repo.isFavorite = true
                }
            }
        }
    }
}