package com.qing.guidetipview

import android.animation.Animator
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 *
 * 类说明:
 *
 * @author qing
 * @time 2018/7/5 17:21
 */
class GuideTipViewBuild(context: Context, targetView: View) {

    private var mGuideTipView: GuideTipView = GuideTipView(context, targetView)

    /**
     * 添加引导提示图 和位置
     */
    fun setGuideIvResAndLocation(@DrawableRes res: Int, guideIvDiffX: Int, guideIvDiffY: Int): GuideTipViewBuild {
        mGuideTipView.mGuideIvRes = res
        mGuideTipView.mGuideIvDiffX = guideIvDiffX
        mGuideTipView.mGuideIvDiffY = guideIvDiffY
        return this
    }


    /**
     * 添加引导提示文字 和位置
     */
    fun setGuideIvMsgAndLocation(tipMsg: String, guideTvDiffX: Int, guideTvDiffY: Int, guidTv: TextView? = null): GuideTipViewBuild {
        guidTv?.let {
            mGuideTipView.mGuideTv = it
        }
        mGuideTipView.mShowGuideTv = true
        mGuideTipView.mGuideTv.text = tipMsg
        mGuideTipView.mGuideTvDiffX = guideTvDiffX
        mGuideTipView.mGuideTvDiffY = guideTvDiffY
        return this
    }


    /**
     * 给引导图片设置属性动画
     */
    fun setContainerForGuide(containerForGuide: ViewGroup): GuideTipViewBuild {
        mGuideTipView.mContainerForGuide = containerForGuide
        return this
    }

    /**
     * 给引导图片设置属性动画
     */
    fun openDebug(debug: Boolean): GuideTipViewBuild {
        mGuideTipView.mIsDebug = debug
        return this
    }


    /**
     * 给引导图片设置属性动画
     */
    fun setGuideIvAnimator(animator: Animator): GuideTipViewBuild {
        mGuideTipView.mGuideIvAnimator = animator
        return this
    }


    /**
     * 设置是否自动消失
     */
    fun setAutoDismiss(autoDismiss: Boolean): GuideTipViewBuild {
        mGuideTipView.mAutoDismiss = autoDismiss
        return this
    }

    /**
     * 设置点击那个区域可以消失
     */
    fun setClickDismissType(clickDismissType: GuideTipView.ClickDismissType): GuideTipViewBuild {
        mGuideTipView.mClickClickDismissType = clickDismissType
        return this
    }


    /**
     * 是否显示点划线
     */
    fun setShowDashPath(showDashPath: Boolean): GuideTipViewBuild {
        mGuideTipView.mShowDashPath = showDashPath
        return this
    }

    /**
     * 设置是否自动消失
     */
    fun setTypeCircle(showCircleGuide: Boolean): GuideTipViewBuild {
        mGuideTipView.mShowCircleGuide = showCircleGuide
        return this
    }

    /**
     * 设置点划线样式
     */
    fun setDashLine(dashStrokeWidth: Float, dashWidth: Float, dashGap: Float, dashOffset: Float): GuideTipViewBuild {
        mGuideTipView.mDashStrokeWidth = dashStrokeWidth
        mGuideTipView.mDashWidth = dashWidth
        mGuideTipView.mDashGap = dashGap
        mGuideTipView.mDashOffset = dashOffset
        return this
    }

    /**
     * 设置背景透明度
     */
    fun setBgAlpha(@IntRange(from = 0, to = 255) bgAlpha: Int): GuideTipViewBuild {
        mGuideTipView.mBgAlpha = bgAlpha
        return this
    }


    /**
     * 设置消失监听
     */
    fun setOnDismissListener(dismissListener: DismissListener): GuideTipViewBuild {
        mGuideTipView.mDismissListener = dismissListener
        return this
    }

    fun build(): GuideTipView {
        return mGuideTipView
    }


}