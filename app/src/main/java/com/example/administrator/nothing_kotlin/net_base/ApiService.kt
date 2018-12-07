package com.example.administrator.nothing_kotlin.net_base

import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.google.gson.JsonObject
import retrofit2.http.GET
import rx.Observable

interface ApiService {

    companion object{
        val BASE_URL : String
            get() = "http://baobab.kaiyanapp.com/api/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<RespHomeData>

}