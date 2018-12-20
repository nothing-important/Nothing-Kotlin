package com.example.administrator.nothing_kotlin.mvp.ui

import android.content.Context
import android.content.Intent
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {

    var urlPath : String = ""

    override fun getLayoutResourse(): Int {
        return R.layout.activity_web
    }

    override fun initView() {

    }

    override fun initData() {
        initIntent()
        initWebSetting(web_view)
        web_view.loadUrl(urlPath)
    }

    private fun initWebSetting(web_view: WebView) {
        val settings = web_view.settings
        settings.setUseWideViewPort(true) //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true) // 缩放至屏幕的大小
        settings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true) //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false) //隐藏原生的缩放控件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE)//关闭webview中缓存
        settings.setAllowFileAccess(true) //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true) //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true) //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8")//设置编码格式
    }

    private fun initIntent() {
        urlPath = intent.getStringExtra(URL_TAG)
    }

    override fun setListener() {
    }

    companion object {
        var URL_TAG = "web_url_path"
        fun trans(context: Context , urlExtra : String){
            var intent = Intent(context , WebActivity::class.java)
            intent.putExtra(URL_TAG , urlExtra)
            context.startActivity(intent)
        }
    }
}