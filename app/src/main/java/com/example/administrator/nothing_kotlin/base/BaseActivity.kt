package com.example.administrator.nothing_kotlin.base

import android.os.Bundle
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.net_base.RxActivity
import com.example.administrator.nothing_kotlin.utils.StatusBarU
import java.math.BigDecimal

abstract class BaseActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourse())
        setStatusBarStatus()
        initView()
        initData()
        setListener()
    }

    abstract fun getLayoutResourse() : Int

    abstract fun initView()

    abstract fun initData()

    abstract fun setListener()

    fun setStatusBarStatus(){
        StatusBarU.transparentStatusBar(this)
        StatusBarU.setLightMode(this , true)
    }

}