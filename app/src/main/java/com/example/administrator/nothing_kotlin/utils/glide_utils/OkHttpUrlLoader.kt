package com.example.administrator.nothing_kotlin.utils.glide_utils

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.Call.Factory
import okhttp3.OkHttpClient
import java.io.InputStream

class OkHttpUrlLoader : ModelLoader<GlideUrl , InputStream> {

    var client: okhttp3.Call.Factory? = null

    constructor(client: Call.Factory?) {
        this.client = client
    }


    override fun buildLoadData(model: GlideUrl, width: Int, height: Int, options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(model, OkHttpFetcher(this.client, model))
    }

    override fun handles(model: GlideUrl): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<GlideUrl, InputStream> {
        var internalClient : Call.Factory? = null
        var client : Call.Factory? = null

        fun getInternal() : Call.Factory{
            if (internalClient == null){
                var var0 = OkHttpUrlLoader.Factory::class.java
                synchronized(OkHttpUrlLoader.Factory::class.java){
                    if (internalClient == null){
                        internalClient = OkHttpClient()
                    }
                }
            }
            return internalClient!!
        }

        constructor(){
            getInternal()
        }

        constructor(client: Call.Factory?){
            this.client = client
        }

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpUrlLoader(this.client)
        }

        override fun teardown() {

        }
    }

}