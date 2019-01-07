package com.example.administrator.nothing_kotlin.utils

import android.util.Log
import kotlin.math.log

class LogU {

    companion object {
        fun e (tag : String , msg : String){
            Log.e(tag , msg)
        }

        fun e (tag : String , msg : Int){
            var logMsg : String = msg.toString()
            Log.e(tag , logMsg)
        }

        fun i (tag : String , msg : String){
            Log.i(tag , msg)
        }
    }

}