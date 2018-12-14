package com.example.administrator.nothing_kotlin.mvp.ui.fragment

import android.support.v4.view.ViewPager
import android.view.View
import android.view.animation.Interpolator
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.adapter.BannerAdapter
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.example.administrator.nothing_kotlin.mvp.contract.HomeContract
import com.example.administrator.nothing_kotlin.mvp.presenter.HomePresenter
import android.widget.Scroller
import java.util.*


class HomeFragment : BaseLazyFragment() , HomeContract.View{

    var presenter : HomePresenter? = null
    var home_viewpager : ViewPager? = null

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        presenter = HomePresenter(context, activity, this)
        home_viewpager = view.findViewById(R.id.home_viewpager)
    }

    override fun initData(isFirstTime : Boolean) {
        if (isFirstTime){
            presenter?.requestData()
        }
    }

    override fun setListener() {
    }

    override fun onRequestHomeDataSuccess(bean : RespHomeData) {

    }

}