package com.example.administrator.nothing_kotlin.bean

data class RespHomeDetailData(var error: Boolean, var results: List<Result>){
    data class Result(var _id: String, var content: String, var cover: String, var crawled: Long, var created_at: String, var deleted: Boolean, var published_at: String, var raw: String, var site: Site, var title: String, var uid: String, var url: String){
        data class Site(var cat_cn: String, var cat_en: String, var desc: String, var feed_id: String, var icon: String, var id: String, var name: String, var subscribers: Int, var type: String, var url: String)
    }
}