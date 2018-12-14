package com.example.administrator.nothing_kotlin.mvp.contract

import com.example.administrator.nothing_kotlin.mvp_base.BasePresenter
import com.example.administrator.nothing_kotlin.mvp_base.BaseView

class TabContract {

    interface Presenter : BasePresenter{
        fun requestData(categoryID : String)
    }

    interface View : BaseView{
        //fun onRequestBranchDataSuccess()
    }

}