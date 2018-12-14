package com.example.administrator.nothing_kotlin.bean

data class RespHomeBranchData(var error: Boolean, var results: List<Result>){
    data class Result(
            var _id: String,
            var created_at: String,
            var icon: String,
            var id: String,
            var title: String
    )
}