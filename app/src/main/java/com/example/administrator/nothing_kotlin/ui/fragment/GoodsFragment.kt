package com.example.administrator.nothing_kotlin.ui.fragment

import android.view.View
import android.widget.Toast
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment

class GoodsFragment : BaseLazyFragment() {

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_goods
    }

    override fun initView(view: View) {
    }

    override fun initData(isFirstTime : Boolean) {
        Toast.makeText(activity,"shangpin",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun setListener() {
    }

}