package com.osmanyalin.githubrepolisting

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RepoListingApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}