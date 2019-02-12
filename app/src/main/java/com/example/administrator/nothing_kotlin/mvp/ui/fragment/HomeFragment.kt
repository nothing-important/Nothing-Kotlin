package com.example.administrator.nothing_kotlin.mvp.ui.fragment

import android.app.Activity
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.adapter.TabAdapter
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.mvp.contract.HomeContract
import com.example.administrator.nothing_kotlin.mvp.presenter.HomePresenter
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch
import com.example.administrator.nothing_kotlin.mvp.ui.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseLazyFragment() , HomeContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

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
        val title_search = view.findViewById<LinearLayout>(R.id.title_search)
        title_search.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_search ->{
                var intent = Intent(activity , SearchActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }

}