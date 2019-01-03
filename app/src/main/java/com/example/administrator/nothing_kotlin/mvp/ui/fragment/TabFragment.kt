package com.example.administrator.nothing_kotlin.mvp.ui.fragment

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
import kotlinx.android.synthetic.main.fragment_home_tab.*
import org.json.JSONObject

class TabFragment : BaseLazyFragment() , TabContract.View, SwipeRefreshLayout.OnRefreshListener, TabDetailAdapter.ItemClick {

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
        home_tab_swipe.setColorSchemeColors(Color.BLACK , Color.RED , Color.BLUE , Color.MAGENTA)
        home_tab_recycler.layoutManager = LinearLayoutManager(activity)
        adapter = TabDetailAdapter(activity as BaseActivity, dataList , this)
        home_tab_recycler.adapter = adapter
    }

    override fun initData(isFirstTime: Boolean) {
        if (isFirstTime){
            home_tab_swipe?.isRefreshing = true
            presenter?.requestData(dataBean?.en_name!!)
        }
    }

    override fun setListener() {
    }


    override fun onRefresh() {
        presenter?.requestData(dataBean?.en_name!!)
    }

    override fun onRequestBranchDataSuccess(beanData: RespHomeDetailData) {
        dataList.clear()
        dataList.addAll(beanData.results)
        adapter?.notifyDataSetChanged()
        home_tab_swipe?.isRefreshing = false
    }

    override fun onRequestBranchDataError(errorMsg: String) {
        home_tab_swipe?.isRefreshing = false
    }

    override fun onItemClick(psn: Int) {
        var result = dataList[psn]
        WebActivity.trans(activity!! , result.url)
    }

}