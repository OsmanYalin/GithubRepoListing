package com.osmanyalin.githubrepolisting.di

import com.osmanyalin.githubrepolisting.network.APIService
import com.osmanyalin.githubrepolisting.network.ApiProvider
import com.osmanyalin.githubrepolisting.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiProvider(apiService: APIService) = ApiProvider(apiService)

    @Singleton
    @Provides
    fun provideUserRepository(apiProvider: ApiProvider) = UserRepository(apiProvider)
}