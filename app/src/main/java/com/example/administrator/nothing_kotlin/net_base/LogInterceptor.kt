package com.example.administrator.nothing_kotlin.net_base

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import android.util.Log
import com.example.administrator.nothing_kotlin.utils.LogU
import okio.Buffer
import java.io.IOException
import okhttp3.MediaType
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset


class LogInterceptor : Interceptor {

    val TAG : String = "retrofit"
    private val UTF8 = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request : Request = chain!!.request()
                .newBuilder()
                .removeHeader("Accept-Encoding")
                .removeHeader("Content-Type")
                .addHeader("Content-Type" , "application/txt;charset=UTF-8")
                .build()
        logForRequest(request)
        logForResponse(chain , request)
        return chain.proceed(request)
    }

    @Throws(IOException::class)
    private fun logForRequest(request: Request) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n" + "--------------------请求信息------------------")
        stringBuilder.append("\n" + "| requestUrl-->" + request.url())//请求地址
        stringBuilder.append("\n" + "| requestMethod-->" + request.method())//请求方法
        //请求头
        stringBuilder.append("\n" + "| requestHeaders:")
        val headers = request.headers()
        val names = headers.names()
        for (name in names) {
            stringBuilder.append("\n| " + name + ":" + headers.get(name))
        }
        //请求体
        var requestBody = "no params"
        val copy = request.newBuilder().build()
        val body = copy.body()
        if (body != null) {
            if (isPlaintext(body.contentType())) {
                val buffer = Buffer()
                body.writeTo(buffer)
                requestBody = buffer.readString(getCharset(body.contentType()!!))
            } else {
                requestBody = "body: maybe [binary body], omitted!"
            }
        }
        stringBuilder.append("\n| requestParams-->$requestBody")
        stringBuilder.append("\n" + "---------------------------------------------")
        LogU.e(TAG , "logForRequest: $stringBuilder")
    }

    @Throws(IOException::class)
    private fun logForResponse(chain: Interceptor.Chain, request: Request) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n" + "| -------------------请求结果-------------------")
        stringBuilder.append("\n" + "| requestUrl-->" + request.url())//请求地址
        stringBuilder.append("\n" + "| requestMethod-->" + request.method())//请求方法
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            stringBuilder.append("\n" + "| HTTP FIELD")
            stringBuilder.append("\n" + "---------------------------------------------")
            Log.e(TAG, "logForResponse: $stringBuilder")
            return
        }

        val copy = response!!.newBuilder().build()
        val body = copy.body()
        val headers = response.headers()
        stringBuilder.append("\n" + "| responseHeaders:")
        var i = 0
        val count = headers.size()
        while (i < count) {
            stringBuilder.append("\n| " + headers.name(i) + ":" + headers.value(i))
            i++
        }
        stringBuilder.append("\n" + "| responseBody:")
        var responseBody = "no responseBody"
        if (body != null) {
            val inputStream = body.byteStream()
            val bos = ByteArrayOutputStream()
            val bytes = ByteArray(4096)
            var len = 0
            while (len != -1){
                bos.write(bytes, 0, len)
                len = inputStream.read(bytes)
            }
            bos.close()
            responseBody = String(bos.toByteArray(), getCharset(body.contentType()!!))
        }
        val s = responseBody
        stringBuilder.append("\n| $s")
        stringBuilder.append("\n---------------------------------------------")
        Log.e(TAG, "logForResponse: $stringBuilder")
    }

    private fun getCharset(contentType: MediaType): Charset {
        return if (contentType.charset() == null) UTF8 else contentType.charset(UTF8)!!
    }

    private fun isPlaintext(mediaType: MediaType?): Boolean {
        if (mediaType == null) return false
        if (mediaType!!.type() != null && mediaType!!.type().equals("text")) {
            return true
        }
        var subtype = mediaType!!.subtype()
        if (subtype != null) {
            subtype = subtype!!.toLowerCase()
            if (subtype!!.contains("x-www-form-urlencoded") || subtype!!.contains("json") || subtype!!.contains("xml") || subtype!!.contains("html"))
            //
                return true
        }
        return false
    }


    fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr) return ""
        val sb = StringBuilder()
        var last = '\u0000'
        var current = '\u0000'
        var indent = 0
        for (i in 0 until jsonStr.length) {
            last = current
            current = jsonStr[i]
            //遇到{ [换行，且下一行缩进
            when (current) {
                '{', '[' -> {
                    sb.append(current)
                    sb.append('\n')
                    indent++
                    addIndentBlank(sb, indent)
                }
            //遇到} ]换行，当前行缩进
                '}', ']' -> {
                    sb.append('\n')
                    indent--
                    addIndentBlank(sb, indent)
                    sb.append(current)
                }
            //遇到,换行
                ',' -> {
                    sb.append(current)
                    if (last != '\\') {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }
        return sb.toString()
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

}