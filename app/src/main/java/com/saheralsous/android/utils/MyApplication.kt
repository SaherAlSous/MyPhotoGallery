package com.saheralsous.android.utils

import android.app.Application
import com.saheralsous.android.database.remote.NetworkApi
import com.saheralsous.android.database.remote.NetworkService
import kotlinx.coroutines.InternalCoroutinesApi

class MyApplication : Application() {

    lateinit var networkApi: NetworkApi

    private val baseUrl = "https://api.flickr.com/"
    @InternalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        networkApi = NetworkService.create(baseUrl, cacheDir, 1024 * 1024 * 10)
    }
}