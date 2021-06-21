package com.saheralsous.android.database.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


private const val API_KEY = "a4c97bc78d162318a884fddb4c9fac78"

class PhotoInterceptor : Interceptor { //page. 540

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newUrl: HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("extras", "url_s")
            .addQueryParameter("safesearch", "1")
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()





        return chain.proceed(newRequest)
    }

}