package com.qing.guidetipsdemo

import android.animation.AnimatorInflater
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import com.qing.guidetipview.DismissListener
import com.qing.guidetipview.GuideTipViewBuild
import com.qing.guidetipview.GuideViewDismissType
import com.qing.guidetipview.util.ScreenUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guide_customlayout.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv_share0.setOnClickListener(this)
        iv_share1.setOnClickListener(this)
        iv_share2.setOnClickListener(this)
        iv_share3.setOnClickListener(this)
        iv_share4.setOnClickListener(this)
        iv_share5.setOnClickListener(this)
        iv_share6.setOnClickListener(this)
        bt_custom.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {


            R.id.iv_share0 -> {
                GuideTipViewBuild(this, iv_share0)
                        //set guide image resource
                        .setGuideIvResAndLocation(R.drawable.img_guide_activity_orginfo_fresh, -ScreenUtil.dp2px(this, 220), ScreenUtil.dp2px(this, 80))
                        //open debug
                        .openDebug(true)
                        //set dismiss listener
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(guideViewDismissType: GuideViewDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        //show
                        .build().show()
            }


            R.id.iv_share1 -> {
                val textView = TextView(this)
                textView.setTextColor(Color.WHITE)
                textView.textSize = 18f
                textView.setBackgroundResource(R.drawable.bg_tv)

                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share1)
                        //set image res
                        .setGuideIvResAndLocation(R.drawable.img_guide_activity_orginfo_fresh, -ScreenUtil.dp2px(this, 220), ScreenUtil.dp2px(this, 80))
                        //set des text
                        .setGuideIvMsgAndLocation("click here dismiss.. ", -ScreenUtil.dp2px(this, 140), ScreenUtil.dp2px(this, 200), textView)
                        //click textView dismiss
                        .setClickDismissType(GuideViewDismissType.DISMISS_IN_GUIDE_TV)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setHighlightAreaIsCircle(false)
                        .setShowDashPath(false)
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(guideViewDismissType: GuideViewDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        .setAutoDismiss(false)
                        //show
                        .build().show()
            }

            R.id.iv_share2 -> {
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share2)
                        //set image res
                        .setGuideIvResAndLocation(R.drawable.img_click, ScreenUtil.dp2px(this, 18), ScreenUtil.dp2px(this, 40))
                        //set des
                        .setGuideIvMsgAndLocation("click highlight area dismiss...", -ScreenUtil.dp2px(this, 180), ScreenUtil.dp2px(this, 90))
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setHighlightAreaIsCircle(false)
                        //show
                        .build().show()
            }
            R.id.iv_share3 -> {
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share3)
                        //set image res
                        .setGuideIvResAndLocation(R.drawable.img_click, ScreenUtil.dp2px(this, 18), ScreenUtil.dp2px(this, 40))
                        //set des
                        .setGuideIvMsgAndLocation("click everywhere to dismiss...", -ScreenUtil.dp2px(this, 180), ScreenUtil.dp2px(this, 90))
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        .setClickDismissType(GuideViewDismissType.DISMISS_IN_ALL_AREA)
                        //set highlight area type
                        .setHighlightAreaIsCircle(false)
                        .setShowDashPath(false)
                        //show
                        .build().show()
            }

            R.id.iv_share4 -> {
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share4)
                        //set image res
                        .setGuideIvResAndLocation(R.drawable.img_click, ScreenUtil.dp2px(this, 18), ScreenUtil.dp2px(this, 40))
                        //set des text
                        .setGuideIvMsgAndLocation("here is describe...", -ScreenUtil.dp2px(this, 140), ScreenUtil.dp2px(this, 90))
                        .setContainerForGuide(ll_main)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setHighlightAreaIsCircle(true)
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(guideViewDismissType: GuideViewDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        .setAutoDismiss(false)
                        //show
                        .build().show()
            }

            R.id.iv_share5 -> {
                // customer guide textView
                val textView = TextView(this)
                textView.setTextColor(Color.WHITE)
                textView.textSize = 18f
                textView.setBackgroundResource(R.drawable.bg_tv)

                //set animation in guideIV
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share5)
                        //set image res
                        .setGuideIvResAndLocation(R.drawable.img_click, ScreenUtil.dp2px(this, 18), ScreenUtil.dp2px(this, 40))
                        //set des text
                        .setGuideIvMsgAndLocation("click here dismiss.. ", -ScreenUtil.dp2px(this, 140), ScreenUtil.dp2px(this, 90), textView)
                        //click textView  to dismiss
                        .setClickDismissType(GuideViewDismissType.DISMISS_IN_GUIDE_TV)
                        //set a container to show guide
                        .setContainerForGuide(ll_main)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setHighlightAreaIsCircle(false)
                        //use dash line
                        .setShowDashPath(false)
                        //setDashLine
                        .setDashLine(3f, 20f, 10f, 0f, Color.parseColor("#000000"))
                        //set translucent background alpha value
                        .setMaskBgColorInt(Color.parseColor("#dd000000"))
                        //dismiss listener
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(guideViewDismissType: GuideViewDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        //forbid auto dismiss
                        .setAutoDismiss(false)
                        //show
                        .build().show()
            }

            R.id.iv_share6 -> {
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share6)
                        //set image res
                        .setGuideIvResAndLocation(R.drawable.img_click, ScreenUtil.dp2px(this, 18), ScreenUtil.dp2px(this, 40))
                        //set des text
                        .setGuideIvMsgAndLocation("forbid click dismiss ", -ScreenUtil.dp2px(this, 140), ScreenUtil.dp2px(this, 90))
                        .setContainerForGuide(ll_main)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setHighlightAreaIsCircle(true)
                        //autoDismiss
                        .setClickDismissType(GuideViewDismissType.DISMISS_AUTO)
                        //dismiss listener
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(guideViewDismissType: GuideViewDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        //if you set  setClickDismissType(GuideViewDismissType.DISMISS_AUTO) then you must set setAutoDismiss(true)
                        //.setAutoDismiss(true)
                        //show
                        .build().show()
            }

            R.id.bt_custom -> {
                showCustomLayoutGuide(bt_custom)
            }
        }
    }


    /**
     * 显示自定义布局引导
     */
    private fun showCustomLayoutGuide(targetView: View) {
        val guideCustomLayout = LayoutInflater.from(this).inflate(R.layout.guide_customlayout, null, false)

        val guideTipView = GuideTipViewBuild(this, targetView)
                .setGuideCustomCenterHorizontal(ScreenUtil.dp2px(this, 45), guideCustomLayout) {
                    //以下是对自定义添加引导布局的操作
                    val alphaAnimation = AlphaAnimation(.5F, 1F)
                    alphaAnimation.duration = 600
                    alphaAnimation.repeatMode = Animation.REVERSE
                    alphaAnimation.repeatCount = Animation.INFINITE
                    alphaAnimation.interpolator = AccelerateInterpolator()
                    it.iv_dot.startAnimation(alphaAnimation)
                    //返回 it 表示点击整个自定义布局都消失
                    //return@setGuideCustomCenterHorizontal it
                    //返回null 表示自动消失
                    //return@setGuideCustomCenterHorizontal null
                    return@setGuideCustomCenterHorizontal it.btn_got_it
                }
                .openDebug(true)
                .setHighlightAreaRadius(ScreenUtil.dp2px(this, 5))
                .setOnDismissListener(object : DismissListener {
                    override fun onDismiss(guideViewDismissType: GuideViewDismissType) {
                        Toast.makeText(this@MainActivity, "clickDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                    }
                })
                .build()

        guideTipView.show()

    }


}
