package com.example.administrator.nothing_kotlin.utils

import android.content.Context

class ScreenU {

    companion object {
        fun dp2px(context: Context , dpValue : Int) : Int{
            val density = context.resources.displayMetrics.density
            return (dpValue * density + 0.5f).toInt()
        }
    }

}