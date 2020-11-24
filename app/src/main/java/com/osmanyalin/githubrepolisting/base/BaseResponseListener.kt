package com.osmanyalin.githubrepolisting.base

interface BaseResponseListener<T> {

    fun onSuccess(data: T?)

    fun onError()

    fun onLoading()
}