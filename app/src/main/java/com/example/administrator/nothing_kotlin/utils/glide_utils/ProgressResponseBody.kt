package com.example.administrator.nothing_kotlin.utils.glide_utils

import android.util.Log
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

class ProgressResponseBody() : ResponseBody() {



    var responseBody : ResponseBody? = null
    var progressListener : ProgressListener? = null
    var bufferedSource : BufferedSource? = null


    constructor(url : String , responseBody: ResponseBody):this(){
        this.responseBody = responseBody
        progressListener = ProgressInterceptor.LISTENER_MAP.get(url)
    }

    override fun contentLength(): Long {
        return responseBody?.contentLength()!!
    }

    override fun contentType(): MediaType? {
        return responseBody?.contentType()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(ProgressSource(responseBody!!.source()))
        }
        return bufferedSource!!
    }

    inner class ProgressSource(source: Source) : ForwardingSource(source) {

        var totalBytesRead : Long = 0

        override fun read(sink: Buffer?, byteCount: Long): Long {
            var bytesRead = super.read(sink, byteCount)
            var fullLength = responseBody?.contentLength()
            var currentProgress : Int = 0
            if (bytesRead.toInt() == -1) {
                totalBytesRead = fullLength!!
            } else {
                totalBytesRead += bytesRead
            }
            var progress = 100f * totalBytesRead / fullLength!!
            Log.d("ooooooooooo", "download progress is " + progress)
            if (progressListener != null && progress.toInt() != currentProgress) {
                progressListener!!.onProgress(progress.toInt())
            }
            if (progressListener != null && totalBytesRead == fullLength) {
                progressListener = null
            }
            currentProgress = progress.toInt()
            return bytesRead
        }
    }

}