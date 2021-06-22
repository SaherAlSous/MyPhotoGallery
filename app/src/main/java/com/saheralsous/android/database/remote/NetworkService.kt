package com.saheralsous.android.database.remote

import android.util.Log
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val NETWORK_TAG = "Network Interceptor"
private const val APP_TAG = "APP Interceptor"


object NetworkService  {

    private const val TIME_OUT =30L
    private lateinit var okHttpClient : OkHttpClient

    fun create(baseUrl: String, cacheDir: File, cacheSize: Long) : NetworkApi {
            okHttpClient = OkHttpClient.Builder()
                .cache(Cache(cacheDir, cacheSize))
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(PhotoInterceptor())
                .addNetworkInterceptor(HttpLoggingInterceptor { message ->
                    Log.d(NETWORK_TAG, message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
        }
}