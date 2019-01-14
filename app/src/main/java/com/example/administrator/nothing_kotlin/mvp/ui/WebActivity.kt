package com.example.administrator.nothing_kotlin.mvp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.*
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.R.id.title_name
import com.example.administrator.nothing_kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {

    var urlPath : String = ""
    var titleName : TextView? = null

    override fun getLayoutResourse(): Int {
        return R.layout.activity_web
    }

    override fun initView() {
        titleName = findViewById(R.id.title_name)
    }

    override fun initData() {
        initIntent()
        initWebSetting(web_view)
        //web_view.webViewClient = MyWebClient()
        //web_view.webChromeClient = MyWebChrome()
        web_view.loadUrl(urlPath)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting(web_view: WebView) {
        //声明WebSettings子类
        val webSettings = web_view.getSettings()
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件
        //其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE//关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8"//设置编码格式
    }

    private fun initIntent() {
        urlPath = intent.getStringExtra(URL_TAG)
        //urlPath = "https://qiuba920.com/openinstall/qiuyouinvite.html"
    }

    override fun setListener() {
    }

    inner class MyWebClient : WebViewClient(){

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    inner class MyWebChrome : WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100){
                this@WebActivity.web_progress.visibility = View.GONE
            }else{
                if (this@WebActivity.web_progress.visibility == View.GONE){
                    this@WebActivity.web_progress.visibility = View.VISIBLE
                }
                this@WebActivity.web_progress.progress = newProgress
            }
        }
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