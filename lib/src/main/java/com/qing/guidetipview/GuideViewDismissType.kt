package com.qing.guidetipview

/**
 * 引导消失类型
 */
enum class GuideViewDismissType {
    //点击高亮消失
    DISMISS_IN_HIGH_LIGHT_AREA,
    //点击指引图片消失
    DISMISS_IN_GUIDE_IV,
    //点击指引文字消失
    DISMISS_IN_GUIDE_TV,
    //点击所有区域消失
    DISMISS_IN_ALL_AREA,
    //自动消失
    DISMISS_AUTO,
    //其他:自定义引导布局点击消失
    DISMISS_OTHER
}