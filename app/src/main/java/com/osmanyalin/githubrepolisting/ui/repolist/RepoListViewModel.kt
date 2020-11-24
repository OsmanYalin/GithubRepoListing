package com.osmanyalin.githubrepolisting.ui.repolist

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.osmanyalin.githubrepolisting.base.BaseViewModel
import com.osmanyalin.githubrepolisting.model.RepoModel
import com.osmanyalin.githubrepolisting.network.Resource
import com.osmanyalin.githubrepolisting.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class RepoListViewModel @ViewModelInject constructor(
    private val repository: UserRepository,
    application: Application) : BaseViewModel(application) {

    private var listRepositories = mutableListOf<RepoModel>()
    val repoItem = MutableLiveData<RepoModel>()

    fun getUserRepos(username: String, page: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val userRepos = repository.getUserRepos(username, page)
            listRepositories.plusAssign(userRepos as MutableList<RepoModel>)
            emit(Resource.success(data = userRepos))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun fetchRepoDetail(repoId: Int) {
        repoItem.value = listRepositories.single { it.id == repoId }
    }
}