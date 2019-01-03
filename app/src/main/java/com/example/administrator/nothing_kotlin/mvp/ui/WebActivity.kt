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
        //声明WebSettings子类
        val webSettings = web_view.getSettings()
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true)
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true) //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true) // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true) //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false) //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)//关闭webview中缓存
        webSettings.setAllowFileAccess(true) //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true) //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true) //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8")//设置编码格式
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