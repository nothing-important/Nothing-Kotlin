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
import com.example.administrator.nothing_kotlin.R.id.viewpager
import android.widget.Scroller
import com.example.administrator.nothing_kotlin.R.id.home
import java.lang.reflect.AccessibleObject.setAccessible
import java.util.*


class HomeFragment : BaseLazyFragment() , HomeContract.View{

    var presenter : HomePresenter? = null
    var mTv : TextView? = null
    var home_viewpager : ViewPager? = null
    var bannerList = ArrayList<String>()
    val SCALE = 0.85f
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var temp = 12

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        presenter = HomePresenter(context, activity, this)
        mTv = view.findViewById(R.id.mTv)
        home_viewpager = view.findViewById(R.id.home_viewpager)
    }

    override fun initData(isFirstTime : Boolean) {
        if (isFirstTime){
            presenter?.requestData()
        }
        bannerList.add("http://img2.imgtn.bdimg.com/it/u=2097124721,3074829049&fm=27&gp=0.jpg")
        bannerList.add("http://img3.imgtn.bdimg.com/it/u=2098739633,3212539388&fm=214&gp=0.jpg")
        bannerList.add("http://img4.imgtn.bdimg.com/it/u=1534542085,1971219363&fm=27&gp=0.jpg")
        initBanner()
    }

    override fun setListener() {
    }

    fun initBanner(){
        var bannerAdapter = BannerAdapter(bannerList , activity?.applicationContext!!)
        home_viewpager?.adapter = bannerAdapter
        home_viewpager?.addOnPageChangeListener(onPageChangeListener)
        home_viewpager?.setPageTransformer(false , pageTransformer)
        setViewPagerScroller(home_viewpager!!)
        home_viewpager?.setCurrentItem(temp)
    }

    override fun onResume() {
        super.onResume()
        startTimer()
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

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i: Int, v: Float, i1: Int) {

        }

        override fun onPageSelected(i: Int) {
            temp = i
        }

        override fun onPageScrollStateChanged(i: Int) {

        }
    }

    private val pageTransformer = ViewPager.PageTransformer { view, position ->
        val v = getPositionConsiderPadding(home_viewpager!!, view)
        if (v >= -1 && v <= 1) {
            // [-1,1]，中间以及相邻的页面，一般相邻的才会用于计算动画
            val scale = SCALE + (1 - SCALE) * (1 - Math.abs(v))
            view.scaleX = scale
            view.scaleY = scale
        } else {
            // [-Infinity,-1)、(1,+Infinity]，超出相邻的范围
            view.scaleX = SCALE
            view.scaleY = SCALE
        }
    }

    /**
     * padding影响了position，自己生成position
     */
    private fun getPositionConsiderPadding(viewPager: ViewPager, page: View): Float {
        val clientWidth = viewPager.measuredWidth - viewPager.paddingLeft - viewPager.paddingRight
        return (page.left - viewPager.scrollX - viewPager.paddingLeft).toFloat() / clientWidth
    }

    /**
     * 设置viewpager自动滑动时，切换的时间
     */
    private fun setViewPagerScroller(viewpager: ViewPager) {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            val interpolator = ViewPager::class.java.getDeclaredField("sInterpolator")
            interpolator.isAccessible = true
            val scroller = object : Scroller(activity, interpolator.get(null) as Interpolator) {
                override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
                    super.startScroll(startX, startY, dx, dy, duration * 8)    // 这里是关键，将duration变长或变短
                }
            }
            scrollerField.set(viewpager, scroller)
        } catch (e: Exception) {
        }

    }

    private fun startTimer() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                temp++
                activity?.runOnUiThread{ home_viewpager?.setCurrentItem(temp) }
            }
        }
        timer?.schedule(timerTask, 1000, 5000)
    }

    private fun stopTimer(){
        if (timer != null){
            timer?.cancel()
            timer = null
        }
        if (timerTask != null){
            timerTask?.cancel()
            timerTask = null
        }
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }
}