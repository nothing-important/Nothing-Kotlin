package com.example.administrator.nothing_kotlin.net_base

import okhttp3.Interceptor
import okhttp3.Response

class InterceptorCookie : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
                                        .newBuilder()
                                        .addHeader("what" , "what")
                                        .build()
        return chain.proceed(request)
    }
}