package com.example.administrator.nothing_kotlin.mvp.ui.fragment

import android.view.View
import android.widget.Toast
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseLazyFragment
import com.example.administrator.nothing_kotlin.utils.glide_utils.GlideU
import kotlinx.android.synthetic.main.fragment_goods.*

class GoodsFragment : BaseLazyFragment() {

    var imgUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3292698820,1724660882&fm=27&gp=0.jpg";

    override fun getLayoutResourse(): Int {
        return R.layout.fragment_goods
    }

    override fun initView(view: View) {
    }

    override fun initData(isFirstTime : Boolean) {
        //GlideU.get().with(activity!!).loadImgNormal(frag_goods_img , imgUrl)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun setListener() {
    }

}