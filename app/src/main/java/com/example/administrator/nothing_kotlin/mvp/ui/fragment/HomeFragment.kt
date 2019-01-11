package com.example.administrator.nothing_kotlin.mvp.ui.fragment

import android.app.Activity
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.adapter.TabAdapter
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.mvp.contract.HomeContract
import com.example.administrator.nothing_kotlin.mvp.presenter.HomePresenter
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseLazyFragment() , HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        presenter?.requestData()
    }

    var presenter : HomePresenter? = null
    var tabAdapter : TabAdapter? = null
    var tabFragments : ArrayList<Fragment> = ArrayList()
    var tabFragmentName : ArrayList<String> = ArrayList()

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        presenter = HomePresenter(context, activity as Activity?, this)
    }

    override fun initData(isFirstTime : Boolean) {
        if (isFirstTime){
            presenter?.requestData()
            tabAdapter = TabAdapter(fragmentManager , tabFragments , tabFragmentName)
            home_viewPager.adapter = tabAdapter
            home_tab.setupWithViewPager(home_viewPager)
        }
    }

    override fun setListener() {
    }

    override fun onRequestHomeDataSuccess(bean : RespXDMainBranch) {
        val results = bean.results
        for (result in results){
            tabFragments.add(TabFragment.getInstance(result))
            tabFragmentName.add(result.name)
        }
        tabAdapter?.notifyDataSetChanged()

    }

}