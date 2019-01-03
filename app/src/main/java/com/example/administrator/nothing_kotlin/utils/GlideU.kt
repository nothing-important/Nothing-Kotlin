package com.example.administrator.nothing_kotlin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.administrator.nothing_kotlin.net_base.RetrofitHelper

class GlideU(var context: Context) {

    var requestOptions: RequestOptions? = null

    init {
        requestOptions = RequestOptions()
        requestOptions?.diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions?.override(400 , 400)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var instance: GlideU? = null

        fun getInstance(context: Context) : GlideU {
            if (instance == null) {
                synchronized(RetrofitHelper::class) {
                    if (instance == null) {
                        instance = GlideU(context)
                    }
                }
            }
            return instance!!
        }
    }


    fun loadImgNormal(imageView: ImageView , imageUrl : String){
        Glide.with(context.applicationContext).load(imageUrl).apply(requestOptions!!).into(imageView)
    }

}