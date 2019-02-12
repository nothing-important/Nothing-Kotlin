package com.example.administrator.nothing_kotlin.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class CommonU {

    companion object {
        fun hideSoftInputFromWindow(activity: Activity){
            var imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken , 0)
        }
    }

}