package com.example.administrator.nothing_kotlin.mvp.contract

import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.example.administrator.nothing_kotlin.mvp_base.BasePresenter
import com.example.administrator.nothing_kotlin.mvp_base.BaseView

interface HomeContract {

    interface Presenter : BasePresenter{
        fun requestData()
    }

    interface View : BaseView{
        fun onRequestHomeDataSuccess(bean : RespHomeData)
    }

}