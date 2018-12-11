package com.example.administrator.nothing_kotlin.utils

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

class StatusBarU {

    companion object {
        /**
         * 使状态栏透明
         */
        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun transparentStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                activity.window.statusBarColor = Color.TRANSPARENT
            } else {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        fun setDarkMode(activity: Activity) {
            setMIUIStatusBarDarkIcon(activity, false)
            setMeizuStatusBarDarkIcon(activity, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }

        /**
         * 修改 MIUI V6  以上状态栏颜色
         */
        private fun setMIUIStatusBarDarkIcon(activity: Activity, darkIcon: Boolean) {
            val clazz = activity.window.javaClass
            try {
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(activity.window, if (darkIcon) darkModeFlag else 0, darkModeFlag)
            } catch (e: Exception) {
                //e.printStackTrace();
            }

        }

        /**
         * 修改魅族状态栏字体颜色 Flyme 4.0
         */
        private fun setMeizuStatusBarDarkIcon(activity: Activity, darkIcon: Boolean) {
            try {
                val lp = activity.window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (darkIcon) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                activity.window.attributes = lp
            } catch (e: Exception) {
                //e.printStackTrace();
            }

        }
    }

}