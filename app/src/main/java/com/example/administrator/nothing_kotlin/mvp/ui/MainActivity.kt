package com.example.administrator.nothing_kotlin.mvp.ui

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.adapter.MainAdapter
import com.example.administrator.nothing_kotlin.base.BaseActivity
import com.example.administrator.nothing_kotlin.mvp.ui.fragment.MineFragment
import com.example.administrator.nothing_kotlin.mvp.ui.fragment.GoodsFragment
import com.example.administrator.nothing_kotlin.mvp.ui.fragment.HomeFragment
import com.example.administrator.nothing_kotlin.views.CustomViewPager

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : BaseActivity(), View.OnClickListener, View.OnScrollChangeListener, ViewPager.OnPageChangeListener {

    var viewPager : CustomViewPager? = null
    var mainAdapter : MainAdapter? = null
    var fragmentList : ArrayList<Fragment> = ArrayList()
    var homeLinear : LinearLayout? = null
    var homeImg : ImageView? = null
    var homeTv : TextView? = null
    var mineLinear : LinearLayout? = null
    var mineImg : ImageView? = null
    var mineTv : TextView? = null
    var goodsLinear : LinearLayout? = null
    var goodsImg : ImageView? = null
    var goodsTv : TextView? = null

    override fun getLayoutResourse(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        viewPager = findViewById(R.id.viewpager)
        viewPager?.addOnPageChangeListener(this)
        homeLinear = findViewById(R.id.home_linear)
        homeImg = findViewById(R.id.home_img)
        homeTv = findViewById(R.id.home_tv)
        mineLinear = findViewById(R.id.mine_linear)
        mineImg = findViewById(R.id.mine_img)
        mineTv = findViewById(R.id.mine_tv)
        goodsLinear = findViewById(R.id.goods_linear)
        goodsImg = findViewById(R.id.goods_img)
        goodsTv = findViewById(R.id.goods_tv)
    }

    override fun initData() {
        var homeFragment = HomeFragment()
        var discoveryFragment = MineFragment()
        var goodsFragment = GoodsFragment()
        fragmentList.add(homeFragment)
        fragmentList.add(goodsFragment)
        fragmentList.add(discoveryFragment)
        var fragmentManager : FragmentManager = supportFragmentManager
        mainAdapter = MainAdapter(fragmentManager , fragmentList)
        viewPager?.adapter = mainAdapter
        viewPager?.setCurrentItem(0)
    }

    override fun setListener() {
        homeLinear?.setOnClickListener(this)
        mineLinear?.setOnClickListener(this)
        goodsLinear?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.mine_linear ->{
                reset()
                setMineSelect()
                viewPager?.setCurrentItem(2)
            }
            R.id.home_linear ->{
                reset()
                setHomeSelect()
                viewPager?.setCurrentItem(0)
            }
            R.id.goods_linear ->{
                reset()
                setGoodsSelect()
                viewPager?.setCurrentItem(1)
            }
        }
    }

    private fun reset(){
        homeImg?.setImageResource(R.mipmap.main_home_unselect_tab)
        homeTv?.setTextColor(resources.getColor(R.color.gray_99))
        mineImg?.setImageResource(R.mipmap.main_mine_unselect_tab)
        mineTv?.setTextColor(resources.getColor(R.color.gray_99))
        goodsImg?.setImageResource(R.mipmap.main_shoping_unselect_tab)
        goodsTv?.setTextColor(resources.getColor(R.color.gray_99))
    }

    private fun setHomeSelect(){
        homeImg?.setImageResource(R.mipmap.main_home_select_tab)
        homeTv?.setTextColor(resources.getColor(R.color.text_color_black))
    }

    private fun setMineSelect(){
        mineImg?.setImageResource(R.mipmap.main_mine_select_tab)
        mineTv?.setTextColor(resources.getColor(R.color.text_color_black))
    }

    private fun setGoodsSelect(){
        goodsImg?.setImageResource(R.mipmap.main_shoping_select_tab)
        goodsTv?.setTextColor(resources.getColor(R.color.text_color_black))
    }

    override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageSelected(p0: Int) {
        reset()
        if (p0 == 0){
            setHomeSelect()
        }else if (p0 == 1){
            setGoodsSelect()
        }else if (p0 == 2){
            setMineSelect()
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

}
