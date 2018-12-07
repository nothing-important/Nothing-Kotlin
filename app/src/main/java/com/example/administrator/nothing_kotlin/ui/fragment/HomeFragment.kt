package com.example.administrator.nothing_kotlin.ui.fragment

import android.view.View
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.bean.RespHomeData
import com.example.administrator.nothing_kotlin.ui.fragment.HomeContract.Presenter

class HomeFragment : BaseLazyFragment() , HomeContract.View{

    var presenter : HomePresenter? = null
    var mTv : TextView? = null

    override fun setPresenter(t: Presenter) {
        this.presenter = presenter
    }

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        presenter = HomePresenter(context, activity , this)
        mTv = view.findViewById(R.id.mTv)
    }

    override fun initData(isFirstTime : Boolean) {
        presenter?.requestData()
    }

    override fun setListener() {
    }

    override fun onRequestHomeDataSuccess(bean : RespHomeData) {
        val issueList = bean.issueList
        var stringBuffer = StringBuffer()
        for (issueBean in issueList){
            val itemList = issueBean.itemList
            for (itemBean in itemList){
                val data = itemBean.data
                val image = data.image
                stringBuffer.append("\n..$image")
            }
        }
        mTv?.setText(stringBuffer)
    }
}