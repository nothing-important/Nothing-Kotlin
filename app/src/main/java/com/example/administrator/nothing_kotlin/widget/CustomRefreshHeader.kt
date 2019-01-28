package com.example.administrator.nothing_kotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle

class CustomRefreshHeader : LinearLayout, RefreshHeader {

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

    override fun onReleasing(percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
    }

    override fun onStartAnimator(layout: RefreshLayout?, height: Int, extendHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout?, oldState: RefreshState?, newState: RefreshState?) {
        when(newState){
            RefreshState.None,RefreshState.PullDownToRefresh ->{
                textView?.text = "下拉刷新"
            }
            RefreshState.Refreshing ->{
                textView?.text = "正在刷新"
            }
            RefreshState.ReleaseToRefresh ->{
                textView?.text = "释放刷新"
            }
            RefreshState.RefreshFinish ->{
                textView?.text = "刷新成功"
            }
            else -> {
            }
        }
    }

    override fun onPullingDown(percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }
}