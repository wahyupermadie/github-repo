package com.wahyupermadie.myapplication.utils.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request

val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
    val originalResponse = chain.proceed(chain.request())
    val cacheControl = originalResponse.header("Cache-Control")
    if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
    ) {
        originalResponse.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, max-age=" + 5000)
            .build()
    } else {
        originalResponse
    }
}

val Context.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE: Interceptor
    get() = Interceptor { chain ->
        var request: Request = chain.request()
        if (!isNetworkAvailable()) {
            request = request.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, only-if-cached, max-stale=")
                .build()
        }
        chain.proceed(request)
    }

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}