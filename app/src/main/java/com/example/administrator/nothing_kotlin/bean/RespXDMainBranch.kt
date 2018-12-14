package com.example.administrator.nothing_kotlin.bean

data class RespXDMainBranch(var error: Boolean, var results: List<Result>){
    data class Result(var _id: String, var en_name: String, var name: String, var rank: Int)
}