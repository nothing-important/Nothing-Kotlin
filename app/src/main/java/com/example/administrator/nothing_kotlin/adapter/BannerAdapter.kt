package com.example.administrator.nothing_kotlin.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.administrator.nothing_kotlin.utils.glide_utils.GlideU
import com.example.administrator.nothing_kotlin.utils.ScreenU
import com.example.administrator.nothing_kotlin.views.RoundImageView


class BannerAdapter(var list : List<String> , var context: Context) : PagerAdapter() {

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = RoundImageView(context)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.setLayoutParams(layoutParams)
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        imageView.setBitmapType(RoundImageView.BitmapType.ROUND)
        imageView.setRoundRadius(ScreenU.dp2px(context , 1))
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideU.getInstance(context).loadImgNormal(imageView , list[position % list.size])
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}