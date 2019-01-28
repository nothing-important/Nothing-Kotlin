package com.example.administrator.nothing_kotlin.mvp.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.adapter.TabDetailAdapter
import com.example.administrator.nothing_kotlin.base.BaseActivity
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.bean.RespHomeDetailData
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch
import com.example.administrator.nothing_kotlin.mvp.contract.TabContract
import com.example.administrator.nothing_kotlin.mvp.presenter.TabPresenter
import com.example.administrator.nothing_kotlin.mvp.ui.WebActivity
import com.example.administrator.nothing_kotlin.utils.LogU
import com.example.administrator.nothing_kotlin.widget.CustomLoadFooter
import com.example.administrator.nothing_kotlin.widget.CustomRefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home_tab.*
import org.json.JSONObject

class TabFragment : BaseLazyFragment() , TabContract.View, TabDetailAdapter.ItemClick, OnRefreshListener, OnLoadmoreListener {

    var dataBean : RespXDMainBranch.Result? = null
    var presenter : TabPresenter? = null
    var adapter : TabDetailAdapter? = null
    var dataList : ArrayList<RespHomeDetailData.Result> = ArrayList()

    companion object {
        fun getInstance(dataBean : RespXDMainBranch.Result) : TabFragment{
            var fragment = TabFragment()
            fragment.dataBean = dataBean
            return fragment
        }
    }

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_home_tab
    }

    override fun initView(view: View) {
        presenter = TabPresenter(context!! , activity as BaseActivity, this)
        home_tab_swipe.setOnRefreshListener(this)
        home_tab_swipe.setOnLoadmoreListener(this)
        home_tab_swipe.refreshHeader = CustomRefreshHeader(activity as BaseActivity)
        home_tab_swipe.refreshFooter = CustomLoadFooter(activity as BaseActivity)
        home_tab_recycler.layoutManager = LinearLayoutManager(activity)
        adapter = TabDetailAdapter(activity as BaseActivity, dataList , this)
        home_tab_recycler.adapter = adapter
    }

    override fun initData(isFirstTime: Boolean) {
        if (isFirstTime){
            home_tab_swipe?.autoRefresh(10)
        }
    }

    override fun setListener() {
    }


    override fun onRefresh(refreshlayout: RefreshLayout?) {
        presenter?.requestData(dataBean?.en_name!!)
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        home_tab_swipe.finishLoadmore(2000)
    }

    override fun onRequestBranchDataSuccess(beanData: RespHomeDetailData) {
        home_tab_swipe?.finishRefresh()
        dataList.clear()
        dataList.addAll(beanData.results)
        adapter?.notifyDataSetChanged()
    }

    override fun onRequestBranchDataError(errorMsg: String) {
        home_tab_swipe?.finishRefresh()
    }

    override fun onItemClick(psn: Int) {
        var result = dataList[psn]
        WebActivity.trans(activity!! , result.url)
    }

}