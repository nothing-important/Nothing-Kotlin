package com.example.administrator.nothing_kotlin.mvp.ui

import android.content.Intent
import android.view.animation.*
import android.widget.TextView
import com.example.administrator.nothing_kotlin.R
import com.example.administrator.nothing_kotlin.base.BaseActivity

class SplashActivity : BaseActivity() {

    var splash_tv : TextView? = null
    val instances by lazy { this }

    override fun getLayoutResourse(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        splash_tv = findViewById(R.id.splash_tv)
    }

    override fun initData() {
        var scaleAnimation = ScaleAnimation(1.0f , 1.5f , 1.0f , 1.5f , ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f)
        var alphaAnimation = AlphaAnimation(0f , 1.0f)
        var animation = AnimationSet(true)
        animation.addAnimation(scaleAnimation)
        animation.addAnimation(alphaAnimation)
        animation.duration = 1500
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                var intent = Intent(instances , MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        splash_tv?.startAnimation(animation)
    }


    override fun setListener() {
    }
}