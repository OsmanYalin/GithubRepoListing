package com.osmanyalin.githubrepolisting.network

import com.osmanyalin.githubrepolisting.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()
        val url = originalRequest.url
            .newBuilder()
            .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .addQueryParameter("client_secret", BuildConfig.CLIENT_SECRET)
            .build()
        originalRequest = originalRequest.newBuilder().url(url).build()

        return chain.proceed(originalRequest)
    }
}
