package com.example.administrator.nothing_kotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.administrator.nothing_kotlin.bean.TabBean

class MainAdapter(fm: FragmentManager? , fragmentList: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    var fragmentList : ArrayList<Fragment>? = null

    init {
        this.fragmentList = fragmentList
    }

    override fun getItem(psn: Int): Fragment {
        return fragmentList!!.get(psn)
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

}