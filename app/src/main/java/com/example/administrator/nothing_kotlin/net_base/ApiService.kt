package com.example.administrator.nothing_kotlin.net_base

import com.example.administrator.nothing_kotlin.bean.RespHomeBranchData
import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.example.administrator.nothing_kotlin.bean.RespHomeDetailData
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ApiService {

    companion object{
        val BASE_URL : String
            get() = "http://gank.io/api/"
        val DEFAULT_NUM = 6
    }

    //获取闲度的主分类
    @GET("xiandu/categories")
    fun getHomeData(): Observable<RespXDMainBranch>

    //获取闲读的子分类
    @GET("xiandu/category/{categoryID}")
    fun getHomeBranch(@Path("categoryID") categoryID : String): Observable<RespHomeBranchData>

    //获取闲读子分类数据
    @GET("xiandu/data/id/{detailID}/count/{detailCount}/page/{detailPage}")
    fun getHomeDetailData(@Path("detailID") detailID : String , @Path("detailCount") detailCount : Int , @Path("detailPage") detailPage : Int) : Observable<RespHomeDetailData>

}