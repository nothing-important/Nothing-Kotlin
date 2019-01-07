package com.example.administrator.nothing_kotlin.utils.glide_utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.net_base.RetrofitHelper
import com.example.administrator.nothing_kotlin.utils.LogU

class GlideU {

    var requestOptions: RequestOptions? = null
    var context : Context? = null

    init {
        //DiskCacheStrategy.NONE： 表示不缓存任何内容。
        //DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
        //DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
        //DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
        requestOptions = RequestOptions()
        requestOptions?.skipMemoryCache(false)//内存缓存
        requestOptions?.diskCacheStrategy(DiskCacheStrategy.NONE)//磁盘缓存，即缓存原始尺寸图片，也缓存转换过后的图片
        requestOptions?.override(Target.SIZE_ORIGINAL/3 , Target.SIZE_ORIGINAL/3)//图片大小
        requestOptions?.error(R.mipmap.img_load_error)

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var instance: GlideU? = null

        fun get() : GlideU {
            if (instance == null) {
                synchronized(RetrofitHelper::class) {
                    if (instance == null) {
                        instance = GlideU()
                    }
                }
            }
            return instance!!
        }
    }

    fun with(context: Context) : GlideU{
        this.context = context
        return this
    }

    fun compressScale(scale : Int) : GlideU{
        requestOptions?.override(Target.SIZE_ORIGINAL/scale , Target.SIZE_ORIGINAL/scale)//图片大小
        return this
    }


    fun loadImgNormal(imageView: ImageView , imageUrl : String){
        ProgressInterceptor.addListener(imageUrl , object : ProgressListener{
            override fun onProgress(progress: Int) {
                LogU.e("what" , progress)
            }
        })
        Glide
                .with(context!!.applicationContext)
                .load(imageUrl)
                .apply(requestOptions!!)
                .listener(object : RequestListener<Drawable>{
                    //boolean 的返回值，false表示未处理、true 表示处理。
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .into(imageView)
    }

}