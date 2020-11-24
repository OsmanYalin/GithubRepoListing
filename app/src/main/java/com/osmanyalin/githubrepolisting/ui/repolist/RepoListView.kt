package com.osmanyalin.githubrepolisting.ui.repolist

import com.osmanyalin.githubrepolisting.base.BaseResponseListener
import com.osmanyalin.githubrepolisting.model.RepoModel

interface RepoListView: BaseResponseListener<List<RepoModel>> {
    fun onSubmit()
}