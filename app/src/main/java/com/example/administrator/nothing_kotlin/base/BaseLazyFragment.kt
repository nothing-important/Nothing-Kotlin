package com.example.administrator.nothing_kotlin.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.nothing_kotlin.net_base.RxFragment

abstract class BaseLazyFragment : RxFragment() {

    var isFirst : Boolean = true
    var isFragmentVisible : Boolean = false
    var isInitView : Boolean = false
    var rootView : View? = null
    var isLoad : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null){
            if (rootView?.parent != null){
                var parent : ViewGroup = rootView?.parent as ViewGroup
                parent.removeView(rootView)
            }
            return rootView
        }
        rootView = inflater.inflate(getLayoutResourse() , container , false)
        isInitView = true
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(rootView!!)
        if (!isLoad && isFragmentVisible){
            initData(isFirst)
            isLoad = true
            isFirst = false
        }
        setListener()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isFragmentVisible = isVisibleToUser
        if (rootView == null)return
        if (isVisibleToUser){//用户可见
            if (isInitView){
                initData(isFirst)
                isLoad = true
                isFirst = false
            }
        }
    }

    abstract fun getLayoutResourse() : Int

    abstract fun initView(view: View)

    abstract fun initData(isFirstTime : Boolean)

    abstract fun setListener()

}