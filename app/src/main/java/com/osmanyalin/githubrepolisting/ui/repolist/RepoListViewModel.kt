package com.osmanyalin.githubrepolisting.ui.repolist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.osmanyalin.githubrepolisting.network.Resource
import com.osmanyalin.githubrepolisting.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class RepoListViewModel @ViewModelInject constructor(private val repository: UserRepository) : ViewModel() {

    fun getUserRepos(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUserRepos(username)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
