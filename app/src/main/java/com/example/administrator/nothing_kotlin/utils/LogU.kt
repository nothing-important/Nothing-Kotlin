package com.example.administrator.nothing_kotlin.utils

import android.util.Log

class LogU {

    companion object {
        fun e (tag : String , msg : String){
            Log.e(tag , msg)
        }

        fun i (tag : String , msg : String){
            Log.i(tag , msg)
        }
    }

}