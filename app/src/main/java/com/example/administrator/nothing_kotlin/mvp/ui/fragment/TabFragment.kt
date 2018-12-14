package com.example.administrator.nothing_kotlin.mvp.ui.fragment

import android.view.View
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseActivity
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch
import com.example.administrator.nothing_kotlin.mvp.contract.TabContract
import com.example.administrator.nothing_kotlin.mvp.presenter.TabPresenter
import com.example.administrator.nothing_kotlin.net_base.ApiService
import com.example.administrator.nothing_kotlin.net_base.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_home_tab.*

class TabFragment : BaseLazyFragment() , TabContract.View{

    var dataBean : RespXDMainBranch.Result? = null
    var presenter : TabPresenter? = null

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
    }

    override fun initData(isFirstTime: Boolean) {
        if (isFirstTime){
            presenter?.requestData(dataBean?.en_name!!)
        }
    }

    override fun setListener() {
    }

    override fun onResume() {
        super.onResume()
        home_tab_tv.setText(dataBean?.name)
    }

}