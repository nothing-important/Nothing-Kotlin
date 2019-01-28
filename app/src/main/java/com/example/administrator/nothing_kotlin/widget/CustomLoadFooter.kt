package com.example.administrator.nothing_kotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle

class CustomLoadFooter : LinearLayout, RefreshFooter {

    constructor(context: Context) : super(context){initView()}
    constructor(context: Context , attrs: AttributeSet?) : super(context , attrs){initView()}
    constructor(context: Context , attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){initView()}

    var textView : TextView? = null

    fun initView () {
        var view : View = LayoutInflater.from(context).inflate(R.layout.custom_refresh_header , this , false)
        textView = view.findViewById(R.id.refresh_tv)
        addView(view)
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun onFinish(layout: RefreshLayout?, success: Boolean): Int {
        return 500
    }

    override fun onInitialized(kernel: RefreshKernel?, height: Int, extendHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun getView(): View {
        return this
    }

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onPullReleasing(percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
    }

    override fun onStartAnimator(layout: RefreshLayout?, height: Int, extendHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout?, oldState: RefreshState?, newState: RefreshState?) {
        when(newState){
            RefreshState.None,RefreshState.PullToUpLoad ->{
                textView?.text = "上拉加载"
            }
            RefreshState.Loading ->{
                textView?.text = "正在加载"
            }
            RefreshState.ReleaseToLoad ->{
                textView?.text = "释放加载"
            }
            RefreshState.LoadFinish ->{
                textView?.text = "加载成功"
            }
            else -> {
            }
        }
    }

    override fun onPullingUp(percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
    }

    override fun setLoadmoreFinished(finished: Boolean): Boolean {
        return false
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return true
    }
}