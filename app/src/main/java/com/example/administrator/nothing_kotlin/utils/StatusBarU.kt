package com.example.administrator.nothing_kotlin.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.example.administrator.nothing_kotlin.R

class StatusBarU {

    companion object {

        val DEFAULT_STATUS_BAR_ALPHA = 112
        private val FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view
        private val FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view
        private val TAG_KEY_HAVE_SET_OFFSET = -123

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

        @TargetApi(Build.VERSION_CODES.M)
        fun setLightMode(activity: Activity, fontIconDark: Boolean) {
            if (fontIconDark) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                } else if (OSU.isMiui()) {
                    setMIUIStatusBarDarkIcon(activity, true)
                } else if (OSU.isFlyme()) {
                    setMeizuStatusBarDarkIcon(activity, true)
                } else {//其他情况下我们将状态栏设置为灰色，就不会看不见字体
                    setColor(activity, Color.GRAY)
                }
            }
        }

        /**
         * 设置状态栏颜色
         *
         * @param activity 需要设置的 activity
         * @param color    状态栏颜色值
         */
        fun setColor(activity: Activity, @ColorInt color: Int) {
            setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA)
        }

        /**
         * 设置状态栏颜色
         *
         * @param activity       需要设置的activity
         * @param color          状态栏颜色值
         * @param statusBarAlpha 状态栏透明度
         */

        fun setColor(activity: Activity, @ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val decorView = activity.window.decorView as ViewGroup
                val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
                if (fakeStatusBarView != null) {
                    if (fakeStatusBarView.visibility == View.GONE) {
                        fakeStatusBarView.visibility = View.VISIBLE
                    }
                    fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
                } else {
                    decorView.addView(createStatusBarView(activity, color, statusBarAlpha))
                }
                setRootView(activity)
            }
        }

        /**
         * 设置根布局参数
         */
        private fun setRootView(activity: Activity) {
            val parent = activity.findViewById<View>(android.R.id.content) as ViewGroup
            var i = 0
            val count = parent.childCount
            while (i < count) {
                val childView = parent.getChildAt(i)
                if (childView is ViewGroup) {
                    childView.setFitsSystemWindows(true)
                    childView.clipToPadding = true
                }
                i++
            }
        }

        /**
         * 生成一个和状态栏大小相同的半透明矩形条
         *
         * @param activity 需要设置的activity
         * @param color    状态栏颜色值
         * @param alpha    透明值
         * @return 状态栏矩形条
         */
        private fun createStatusBarView(activity: Activity, @ColorInt color: Int, alpha: Int): View {
            // 绘制一个和状态栏一样高的矩形
            val statusBarView = View(activity)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
            statusBarView.layoutParams = params
            statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
            statusBarView.id = FAKE_STATUS_BAR_VIEW_ID
            return statusBarView
        }

        /**
         * 获取状态栏高度
         *
         * @param context context
         * @return 状态栏高度
         */
        private fun getStatusBarHeight(context: Context): Int {
            // 获得状态栏高度
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }

        /**
         * 计算状态栏颜色
         *
         * @param color color值
         * @param alpha alpha值
         * @return 最终的状态栏颜色
         */
        private fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
            if (alpha == 0) {
                return color
            }
            val a = 1 - alpha / 255f
            var red = color shr 16 and 0xff
            var green = color shr 8 and 0xff
            var blue = color and 0xff
            red = (red * a + 0.5).toInt()
            green = (green * a + 0.5).toInt()
            blue = (blue * a + 0.5).toInt()
            return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
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