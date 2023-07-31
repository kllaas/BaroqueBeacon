package com.example.baroquebeacon.data.interceptor

import com.example.baroquebeacon.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().url.newBuilder()
            .addQueryParameter(QUERY_PARAM_API_KEY, BuildConfig.API_KEY)
            .build()
        return chain.proceed(
            chain.request().newBuilder()
                .url(request)
                .build()
        )
    }

    private companion object {
        const val QUERY_PARAM_API_KEY = "key"
    }
}