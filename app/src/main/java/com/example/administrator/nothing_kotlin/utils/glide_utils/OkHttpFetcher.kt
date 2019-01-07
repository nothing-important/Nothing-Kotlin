package com.example.administrator.nothing_kotlin.utils.glide_utils

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import okhttp3.*
import java.io.IOException
import java.io.InputStream

class OkHttpFetcher : DataFetcher<InputStream>{

    var okhttpClient : okhttp3.Call.Factory? = null
    var url : GlideUrl? = null
    var stream : InputStream? = null
    var responseBody : ResponseBody? = null
    var call : Call? = null
    var isCancelled : Boolean = false

    constructor(okhttpClient: okhttp3.Call.Factory?, url: GlideUrl?) {
        this.okhttpClient = okhttpClient
        this.url = url
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

    override fun cleanup() {
        try {
            if (stream != null){
                stream?.close()
            }
            if (responseBody != null){
                responseBody?.close()
            }
        }catch (e : Exception){

        }
    }

    override fun cancel() {
        var local = call
        if (local != null){
            local.cancel()
        }
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        var requestBuilder = Request.Builder().url(url?.toStringUrl())
        var request = url?.getHeaders()?.entries?.iterator()
        while(request?.hasNext()!!) {
            var headerEntry = request.next()
            var key = headerEntry.key
            requestBuilder.addHeader(key, headerEntry.value)
        }

        var request1 = requestBuilder.build()
        call = okhttpClient?.newCall(request1)
        call?.enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                callback.onLoadFailed(e!!)
            }

            override fun onResponse(call: Call?, response: Response?) {
                responseBody = response?.body()
                if(response?.isSuccessful()!!) {
                    var contentLength = responseBody?.contentLength()
                    stream = ContentLengthInputStream.obtain(responseBody?.byteStream()!!, contentLength!!)
                    callback.onDataReady(stream)
                } else {
                    callback.onLoadFailed(HttpException(response.message(), response.code()))
                }
            }
        })
    }
}