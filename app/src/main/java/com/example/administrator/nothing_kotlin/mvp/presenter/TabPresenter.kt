package com.example.administrator.nothing_kotlin.mvp.presenter

import android.content.Context
import com.example.administrator.nothing_kotlin.base.BaseActivity
import com.example.administrator.nothing_kotlin.bean.RespHomeBranchData
import com.example.administrator.nothing_kotlin.bean.RespHomeDetailData
import com.example.administrator.nothing_kotlin.mvp.contract.TabContract
import com.example.administrator.nothing_kotlin.net_base.ApiService
import com.example.administrator.nothing_kotlin.net_base.NetU
import com.example.administrator.nothing_kotlin.net_base.RetrofitHelper
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1
import rx.schedulers.Schedulers

class TabPresenter(var context: Context , var activity: BaseActivity , var view : TabContract.View) : TabContract.Presenter {

    var id : String? = null

    override fun requestData(categoryID: String) {
        val apiService = RetrofitHelper.getInstance(ApiService.BASE_URL).create()
        val homeBranch = apiService?.getHomeBranch(categoryID)
        homeBranch?.doOnNext(object : Action1<RespHomeBranchData>{
                    override fun call(t: RespHomeBranchData?) {
                        id = t?.results?.get(0)?.id
                    }
                })?.flatMap(object : Func1<RespHomeBranchData, Observable<RespHomeDetailData>> {
                    override fun call(t: RespHomeBranchData?): Observable<RespHomeDetailData>{
                        return apiService.getHomeDetailData(id!! , ApiService.DEFAULT_NUM , 1)
                    }

                })?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<RespHomeDetailData>() {
                    override fun onNext(t: RespHomeDetailData?) {

                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                    }
                })
    }
}