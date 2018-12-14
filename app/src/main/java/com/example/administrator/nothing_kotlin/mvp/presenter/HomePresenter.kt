package com.example.administrator.nothing_kotlin.mvp.presenter

import android.app.Activity
import android.content.Context
import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch
import com.example.administrator.nothing_kotlin.mvp.contract.HomeContract
import com.example.administrator.nothing_kotlin.net_base.ApiService
import com.example.administrator.nothing_kotlin.net_base.NetU
import com.example.administrator.nothing_kotlin.net_base.NetU.RequestListener
import com.example.administrator.nothing_kotlin.net_base.RetrofitHelper

class HomePresenter(var context: Context?, var activity: Activity?, var view: HomeContract.View) : HomeContract.Presenter{

    override fun requestData() {
        val homeData = RetrofitHelper.getInstance(ApiService.BASE_URL).create()?.getHomeData()
        NetU<RespXDMainBranch>().with(context!!).isShowLoadDialog(true).build(homeData!!).request(object : RequestListener<RespXDMainBranch> {
            override fun onRequestSuccess(t: RespXDMainBranch) {
                view.onRequestHomeDataSuccess(t)
            }

            override fun onRequestError(t: RespXDMainBranch) {
            }

            override fun onSystemError(errorMsg: String) {
            }

        })
    }
}