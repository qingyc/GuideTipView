package com.qing.guidetipview.util

import android.content.Context

/**
 *
 * 类说明: 获取屏幕参数工具类
 *
 * @author qing
 * @time 23/04/2018 18:16
 */
object ScreenUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}