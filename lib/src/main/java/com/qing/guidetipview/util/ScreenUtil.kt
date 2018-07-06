package com.qing.guidetipview.util

import android.content.Context
import android.util.Log
import android.util.TypedValue

/**
 *
 * 类说明: 获取屏幕参数工具类
 *
 * @author qing
 * @time 23/04/2018 18:16
 */
object ScreenUtil {

    /**
     * 设计稿屏幕尺寸
     */
    const val DESIGN_SCREEN_WIDTH = 1080
    const val DESIGN_SCREEN_HEIGHT = 1920

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 字体大小适配
     * @param spValue
     * @return
     */
    fun spToPx(context: Context, spValue: Int): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue.toFloat(), metrics).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * 默认1920x 1080   根据屏幕宽度 缩放获取图片绘制时应该的尺寸
     */
    fun getBitmapScaledValue(context: Context, value: Int): Int {
        return value * context.resources.displayMetrics.widthPixels / 1080

    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        //val wm =context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    fun getScreenHeight(context: Context): Int {

        val heightPixels = context.resources.displayMetrics.heightPixels
        return heightPixels
    }


    /**
     * 获取虚拟按键高度
     *
     * @param context
     * @return
     */
    fun getNavigationBarHeight(context: Context): Int {
        var height = 0
        val isVirtualKeyShow = isVirtualKeyShow(context)
        if (isVirtualKeyShow) {
            try {
                val resources = context.resources
                val resourceId = resources.getIdentifier("navigation_bar_height_landscape", "dimen", "android")
                height = resources.getDimensionPixelSize(resourceId)
                Log.v("dbw", "Navi height:$height")
            } catch (e: Exception) {
                height = 0
                e.printStackTrace()
            }

        }
        return height
    }

    /**
     * 判断是否有虚拟按键
     *
     * @param context
     * @return
     */
    private fun isVirtualKeyShow(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
            Log.e("dbw", e.message)
        }
        return hasNavigationBar
    }
}