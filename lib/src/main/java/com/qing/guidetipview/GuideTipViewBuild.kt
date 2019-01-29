package com.qing.guidetipview

import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 *
 * 类说明:新手引导View的构建
 *
 * @author qing
 * @time 2018/7/5 17:21
 *
 * @param context 上下文
 * @param targetView 需要被高亮的目标view
 */
class GuideTipViewBuild(context: Context, targetView: View) {

    /**
     * 新手引导view(包含背景/高亮区显示/新手引导的文字或者图片或者自定义布局)
     */
    private var mGuideTipView: GuideTipView = GuideTipView(context, targetView)

    /**
     * 添加引导提示图和位置
     * @param guideIvRes 用作新手引导的图片的资源
     * @param guideIvDiffX  引导图片左上相对高亮控件左上 x方向相对距离
     * @param guideIvDiffY   引导图片左上相对高亮控件左上 y方向相对距离
     */
    fun setGuideIvResAndLocation(@DrawableRes guideIvRes: Int, guideIvDiffX: Int, guideIvDiffY: Int): GuideTipViewBuild {
        mGuideTipView.mGuideIvRes = guideIvRes
        mGuideTipView.mGuideIvDiffX = guideIvDiffX
        mGuideTipView.mGuideIvDiffY = guideIvDiffY
        return this
    }


    /**
     * 添加引导提示文字 和位置
     *
     * @param tipMsg 提示文字的内容
     * @param guideTvDiffX  引导提示文字左上相对高亮控件左上 x方向相对距离
     * @param guideTvDiffY   引导提示文字左上相对高亮控件左上 y方向相对距离
     * @param guideTv  传入自定义的引导提示文字的TextView 如果不传入 使用默认的一个样式的TextView显示提示文字
     */
    fun setGuideIvMsgAndLocation(tipMsg: String, guideTvDiffX: Int, guideTvDiffY: Int, guideTv: TextView? = null): GuideTipViewBuild {
        guideTv?.let {
            mGuideTipView.mGuideTv = it
        }
        mGuideTipView.mShowGuideTv = true
        mGuideTipView.mGuideTv.text = tipMsg
        mGuideTipView.mGuideTvDiffX = guideTvDiffX
        mGuideTipView.mGuideTvDiffY = guideTvDiffY
        return this
    }


    /**
     * 添加自定义布局的引导提示和相对位置
     *
     * @param guideCustomLayoutDiffX  自定义布局的引导提示相对高亮控件左上 x方向相对距离
     * @param guideCustomLayoutDiffY   自定义布局的引导提示相对高亮控件左上 y方向相对距离
     * @param customLayout  自定义的一个view 作为显示引导
     */
    fun setGuideCustomLayoutAndLocation(guideCustomLayoutDiffX: Int, guideCustomLayoutDiffY: Int, customLayout: View): GuideTipViewBuild {
        mGuideTipView.setCustomGuideLayout(guideCustomLayoutDiffX, guideCustomLayoutDiffY, customLayout)
        return this
    }

    /**
     * 添加自定义布局(居中显示)
     * @param guideCustomLayoutDiffY 自定义布局相对于高亮控件左上 y方向相对距离
     */
    fun setGuideCustomCenterHorizontal(guideCustomLayoutDiffY: Int, customLayout: View): GuideTipViewBuild {
        mGuideTipView.setCustomGuideLayout(guideCustomLayoutDiffY, customLayout)
        return this
    }


    /**
     * 设置引导显示的外部容器
     * @param containerForGuide  默认引导是添加显示Activity的根布局,也可以显示在一个自定义的ViewGroup中
     */
    fun setContainerForGuide(containerForGuide: ViewGroup): GuideTipViewBuild {
        mGuideTipView.mContainerForGuide = containerForGuide
        return this
    }

    /**
     * 给引导图片设置属性动画
     * @param animatorForGuideIv 给引导图片设置动画
     */
    fun setGuideIvAnimator(animatorForGuideIv: Animator): GuideTipViewBuild {
        mGuideTipView.mGuideIvAnimator = animatorForGuideIv
        return this
    }

    /**
     * 打开调试模式
     * @param debug true:调试时消失后可以再次出现/false: 只出现一次
     */
    fun openDebug(debug: Boolean): GuideTipViewBuild {
        mGuideTipView.mIsDebug = debug
        return this
    }


    /**
     * 设置是否自动消失 (默认4秒后自动消失)
     */
    fun setAutoDismiss(autoDismiss: Boolean): GuideTipViewBuild {
        mGuideTipView.mAutoDismiss = autoDismiss
        return this
    }

    /**
     * 设置点击哪个区域可以消失
     * @param guideViewDismissType 点击哪里使新手引导消失
     */
    fun setClickDismissType(guideViewDismissType: GuideViewDismissType): GuideTipViewBuild {
        mGuideTipView.mClickGuideViewDismissType = guideViewDismissType
        return this
    }


    /**
     * 高亮区边框是否显示点划线
     * @param showDashPath 显示点划线
     */
    fun setShowDashPath(showDashPath: Boolean): GuideTipViewBuild {
        mGuideTipView.mShowDashPath = showDashPath
        return this
    }

    /**
     * 点划线和高亮区边缘的偏移
     */
    fun setShowDashPathOffset(dashOffset: Float): GuideTipViewBuild {
        mGuideTipView.mDashOffset = dashOffset
        return this
    }


    /**
     * 设置高亮区边框点划线样式(可以不设置,不设置使用一个默认的样式)
     * @param dashStrokeWidth 点划线宽度
     * @param dashWidth 点划线点宽度
     * @param dashGap 点划线间隔宽度
     * @param  dashOffset 点划线起始偏移(实际没什么用)
     * @param  dashLineColor  点划线颜色 默认白色
     */
    fun setDashLine(dashStrokeWidth: Float, dashWidth: Float, dashGap: Float, dashOffset: Float, dashLineColor: Int = Color.WHITE): GuideTipViewBuild {
        mGuideTipView.mDashStrokeWidth = dashStrokeWidth
        mGuideTipView.mDashWidth = dashWidth
        mGuideTipView.mDashGap = dashGap
        mGuideTipView.mDashOffset = dashOffset
        mGuideTipView.mDashLineColor = dashLineColor
        return this
    }


    /**
     * 设置高亮区是一个圆形
     * @param highlightAreaIsCircle true:默认圆形高亮区/false:矩形高亮区
     */
    fun setHighlightAreaIsCircle(highlightAreaIsCircle: Boolean): GuideTipViewBuild {
        mGuideTipView.mHighlightAreaIsCircle = highlightAreaIsCircle
        return this
    }

    /**
     * 设置一个padding 防止高亮区太小
     */
    fun setHighlightAreaPadding(highlightPadding: Int): GuideTipViewBuild {
        mGuideTipView.mHighlightPadding = highlightPadding
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

    /**
     * 设置高亮区域是否根据目标控件的大小自定倒圆角
     * @param highlightAreaAutoRound 高亮区根据需要高亮的控件的大小自动倒圆角
     */
    fun setHighlightAreaAutoRound(highlightAreaAutoRound: Boolean = false): GuideTipViewBuild {
        mGuideTipView.mHighlightAreaAutoRound = highlightAreaAutoRound
        return this
    }

    /**
     * 设置高亮半径
     */
    fun setHighlightAreaRadius(highlightAreaUserSetRadius: Int): GuideTipViewBuild {
        mGuideTipView.mHighlightAreaUserSetRadius = highlightAreaUserSetRadius
        return this
    }

    fun build(): GuideTipView {
        return mGuideTipView
    }


}