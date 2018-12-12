package com.example.administrator.nothing_kotlin.views


import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView

/**
 * Created by Administrator on 2018/5/16.
 */

class CustomScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {


    var parentScrollView: CustomScrollView? = null
    private var scrollChange: ScrollChange? = null

    private var lastScrollDelta = 0

    internal var mTop = 10

    private val scrollRange: Int
        get() {
            var scrollRange = 0
            if (childCount > 0) {
                val child = getChildAt(0)
                scrollRange = Math.max(0, child.height - height)
            }
            return scrollRange
        }

    internal var currentY: Int = 0

    fun resume() {
        overScrollBy(0, -lastScrollDelta, 0, scrollY, 0, scrollRange, 0, 0, true)
        lastScrollDelta = 0
    }

    /**
     * 将targetView滚到最顶端
     */
    fun scrollTo(targetView: View) {
        val oldScrollY = scrollY
        val top = targetView.top - mTop
        val delatY = top - oldScrollY
        lastScrollDelta = delatY
        overScrollBy(0, delatY, 0, scrollY, 0, scrollRange, 0, 0, true)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (parentScrollView == null) {
            return super.onInterceptTouchEvent(ev)
        } else {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                // 将父scrollview的滚动事件拦截
                currentY = ev.y.toInt()
                setParentScrollAble(false)
                return super.onInterceptTouchEvent(ev)
            } else if (ev.action == MotionEvent.ACTION_UP) {
                // 把滚动事件恢复给父Scrollview
                setParentScrollAble(true)
            } else if (ev.action == MotionEvent.ACTION_MOVE) {
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val child = getChildAt(0)
        if (parentScrollView != null) {
            if (ev.action == MotionEvent.ACTION_MOVE) {
                var height = child.measuredHeight
                height = height - measuredHeight
                // System.out.println("height=" + height);
                val scrollY = scrollY
                // System.out.println("scrollY" + scrollY);
                val y = ev.y.toInt()
                // 手指向下滑动
                if (currentY < y) {
                    if (scrollY <= 0) {
                        // 如果向下滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true)
                        return false
                    } else {
                        setParentScrollAble(false)
                    }
                } else if (currentY > y) {
                    if (scrollY >= height) {
                        // 如果向上滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true)
                        return false
                    } else {
                        setParentScrollAble(false)
                    }
                }
                currentY = y
            }
        }
        return super.onTouchEvent(ev)
    }

    /**
     * 是否把滚动事件交给父scrollview
     *
     * @param flag
     */
    private fun setParentScrollAble(flag: Boolean) {
        parentScrollView!!.requestDisallowInterceptTouchEvent(!flag)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (scrollChange != null) {
            scrollChange!!.onScrollChange(l, t, oldl, oldt)
        }
    }

    interface ScrollChange {
        fun onScrollChange(l: Int, t: Int, oldl: Int, oldt: Int)
    }

    fun addOnScrollChange(scrollChange: ScrollChange) {
        this.scrollChange = scrollChange
    }
}