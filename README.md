# GuideTipView
A view  let you  highlight a special area and show a tip  

Screenshots
-----------
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20180706-170549-HD.gif)
#### use in activity
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20180706-170859.gif)
#### use in viewGroup
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20180706-171010.gif)
#### use customLayout
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/WX20190102-182721.png)
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20190102-174709.gif)

Usage
----

look here
https://www.jianshu.com/p/3206092a5751

# How To Use
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	          implementation 'com.github.qingyc:GuideTipView:0.4'
	}

简单使用
simple use 
```
 GuideTipViewBuild(context, targetIv)
   .setGuideIvResAndLocation(R.drawable.guide_iv, locationX, locationY)
   .build()
   .show()
```
定制
Customization
```
 // customer guide textView
 
                val textView = TextView(this)
                textView.setTextColor(Color.WHITE)
                textView.textSize = 18f
                textView.setBackgroundResource(R.drawable.bg_tv)

                //set animation in guideIV
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.guide_up_down)
                GuideTipViewBuild(this, iv_share5)
                        //set image res and set location 
                        .setGuideIvResAndLocation(R.drawable.img_click, ScreenUtil.dp2px(this, 18), ScreenUtil.dp2px(this, 40))
                        //set des text and set location
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
                        .setDashLine(3f,20f,10f,0f)
                        //set translucent background alpha value
                        .setBgAlpha(200)
                        //dismiss listener
                        .setOnDismissListener(object : DismissListener {
                            override fun onDismiss(guideViewDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "guideViewDismissType = $guideViewDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        //forbid auto dismiss
                        .setAutoDismiss(false)
                        //show
                        .build().show()
```
 使用自定义布局
 use customLayout 
 
 
    private fun showCustomLayoutGuide(targetView: View) {

        val alphaAnimation = AlphaAnimation(.5F, 1F)
        alphaAnimation.duration = 600
        alphaAnimation.repeatMode = Animation.REVERSE
        alphaAnimation.repeatCount = Animation.INFINITE
        alphaAnimation.interpolator = AccelerateInterpolator()
        
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
                    override fun onDismiss(guideViewDismissType: GuideTipView.ClickDismissType) {

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

# Thanks

[fredericojssilva/ShowTipsView](https://github.com/fredericojssilva/ShowTipsView)

License
-------
MIT License

Copyright (c) 2018 qingyc

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
