package com.example.administrator.nothing_kotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.administrator.nothing_kotlin.bean.RespXDMainBranch

class TabAdapter(fm: FragmentManager?, var fragmentList: ArrayList<Fragment> , var nameList : ArrayList<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(psn: Int): Fragment {
        return fragmentList!!.get(psn)
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return nameList.get(position)
    }

}