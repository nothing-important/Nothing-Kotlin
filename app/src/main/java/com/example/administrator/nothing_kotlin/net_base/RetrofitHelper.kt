package com.example.administrator.nothing_kotlin.net_base

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper(baseUrl:String) {

    //var cache : Cache? = null
    var okHttpClient : OkHttpClient? = null
    var retrofit : Retrofit? = null
    val DEFAULT_TIMEOUT : Long = 10
    val url = baseUrl
    init {
        var gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create()
        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(InterceptorLog())
                //.addInterceptor(InterceptorCookie())
                //.cache(cache)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build()

    }
    companion object{
        @Volatile
        var instance: RetrofitHelper? = null

        fun getInstance(baseUrl: String) : RetrofitHelper {
            if (instance == null) {
                synchronized(RetrofitHelper::class) {
                    if (instance == null) {
                        instance = RetrofitHelper(baseUrl)
                    }
                }
            }
            return instance!!
        }
    }

    fun create(): ApiService? {
//        if (service == null) {
//            throw RuntimeException("Api service is null!")
//        }
        return retrofit?.create(ApiService::class.java)
    }

}