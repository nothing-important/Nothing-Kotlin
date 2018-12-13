package com.example.administrator.nothing_kotlin.net_base

import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.google.gson.JsonObject
import retrofit2.http.GET
import rx.Observable

interface ApiService {

    companion object{
        val BASE_URL : String
            get() = " http://gank.io/api/"
    }

    //获取首页第一页数据
    @GET("xiandu/categories")
    fun getHomeData(): Observable<RespHomeData>

}