package com.osmanyalin.githubrepolisting.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.osmanyalin.githubrepolisting.BuildConfig
import com.osmanyalin.githubrepolisting.network.APIKeyInterceptor
import com.osmanyalin.githubrepolisting.network.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()

    if (BuildConfig.DEBUG)
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    else
      loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

    return OkHttpClient.Builder()
      .addInterceptor(APIKeyInterceptor())
      .addInterceptor(loggingInterceptor)
      .build()
  }

  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Provides
  fun provideAPIService(retrofit: Retrofit): APIService {
    return retrofit.create(APIService::class.java)
  }
}
