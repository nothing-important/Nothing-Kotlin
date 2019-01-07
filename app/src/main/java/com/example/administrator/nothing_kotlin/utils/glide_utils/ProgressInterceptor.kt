package com.example.administrator.nothing_kotlin.utils.glide_utils

import okhttp3.Interceptor
import okhttp3.Response

class ProgressInterceptor : Interceptor {

    companion object {
        var LISTENER_MAP = HashMap<String , ProgressListener>()

        fun addListener(url : String , progressListener: ProgressListener){
            LISTENER_MAP.put(url , progressListener)
        }

        fun removeListener(url: String){
            LISTENER_MAP.remove(url)
        }
    }



    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
        val proceed = chain?.proceed(request)
        val url = request?.url().toString()
        val body = proceed?.body()
        val build = proceed?.newBuilder()?.body(ProgressResponseBody(url, body!!))?.build()
        return build!!
    }

}