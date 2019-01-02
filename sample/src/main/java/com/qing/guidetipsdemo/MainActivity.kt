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
import com.qing.guidetipview.GuideTipView
import com.qing.guidetipview.GuideTipViewBuild
import com.qing.guidetipview.util.ScreenUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guide_usage_click.view.*

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
                            override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $clickDismissType", Toast.LENGTH_SHORT).show()
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
                        .setClickDismissType(GuideTipView.ClickDismissType.DISMISS_IN_GUIDE_TV)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setTypeCircle(false)
                        .setShowDashPath(false)
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $clickDismissType", Toast.LENGTH_SHORT).show()
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
                        .setTypeCircle(false)
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
                        .setClickDismissType(GuideTipView.ClickDismissType.DISMISS_IN_ALL_AREA)
                        //set highlight area type
                        .setTypeCircle(false)
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
                        .setContainerForGuide(rl_main)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setTypeCircle(true)
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $clickDismissType", Toast.LENGTH_SHORT).show()
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
                        .setClickDismissType(GuideTipView.ClickDismissType.DISMISS_IN_GUIDE_TV)
                        //set a container to show guide
                        .setContainerForGuide(rl_main)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setTypeCircle(false)
                        //use dash line
                        .setShowDashPath(false)
                        //setDashLine
                        .setDashLine(3f, 20f, 10f, 0f)
                        //set translucent background alpha value
                        .setBgAlpha(200)
                        //dismiss listener
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $clickDismissType", Toast.LENGTH_SHORT).show()
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
                        .setContainerForGuide(rl_main)
                        //open debug
                        .openDebug(true)
                        //add animation in guide iv
                        .setGuideIvAnimator(loadAnimator)
                        //set highlight area type
                        .setTypeCircle(true)
                        //autoDismiss
                        .setClickDismissType(GuideTipView.ClickDismissType.AUTO_DISMISS)
                        //dismiss listener
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $clickDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        //if you set  setClickDismissType(GuideTipView.ClickDismissType.AUTO_DISMISS) then you must set setAutoDismiss(true)
                        //.setAutoDismiss(true)
                        //show
                        .build().show()
            }

            R.id.bt_custom -> {
                showCustomLayoutGuide(bt_custom)
            }
        }
    }



    private fun showCustomLayoutGuide(targetView: View) {

        val alphaAnimation = AlphaAnimation(.5F, 1F)
        alphaAnimation.duration = 600
        alphaAnimation.repeatMode = Animation.REVERSE
        alphaAnimation.repeatCount = Animation.INFINITE
        alphaAnimation.interpolator = AccelerateInterpolator()
        //点击整个卡片，查看详细使用情况
        val guideCustomLayoutForClickItem = LayoutInflater.from(this).inflate(R.layout.guide_usage_click, null, false)
        val guideTipView = GuideTipViewBuild(this, targetView)
                .setClickDismissType(GuideTipView.ClickDismissType.AUTO_DISMISS)
                .setGuideCustomLayoutAndLocation(ScreenUtil.dp2px(this, -5), ScreenUtil.dp2px(this, 45), guideCustomLayoutForClickItem)
                .openDebug(true)
                .setTypeCircle(false)
                .setShowDashPath(false)
                .setBgAlpha(100)
                .setAutoRadius(true)
                //dismiss listener
                .setOnDismissListener(object : DismissListener {
                    override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {

                    }
                })
                .setAutoDismiss(false)
                //show
                .build()

        guideTipView.show()
        guideCustomLayoutForClickItem.iv_dot.startAnimation(alphaAnimation)
        guideCustomLayoutForClickItem.btn_got_it.setOnClickListener {
            guideTipView.dismiss()
        }
    }


}
