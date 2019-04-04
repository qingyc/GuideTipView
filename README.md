# GuideTipView
A view  let you  highlight a special area and show a tip  

Screenshots
-----------
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/guideTipView-HD.gif)


Usage
----

look here
https://www.jianshu.com/p/3206092a5751

[Download Demo apk](https://github.com/qingyc/GuideTipView/blob/master/sample/release/sample-release.apk)


# How To Use
Step 1.Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency
```
dependencies {
          implementation 'com.github.qingyc:GuideTipView:0.6'
}
```	
Step 3.
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
```
 使用自定义布局
 use customLayout 
 ```

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
```
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
