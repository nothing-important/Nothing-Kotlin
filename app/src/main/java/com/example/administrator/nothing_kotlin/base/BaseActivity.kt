package com.example.administrator.nothing_kotlin.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.nothing_kotlin.net_base.RxActivity

abstract class BaseActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourse())
        initView()
        initData()
        setListener()
    }

    abstract fun getLayoutResourse() : Int

    abstract fun initView()

    abstract fun initData()

    abstract fun setListener()

}