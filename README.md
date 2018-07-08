# GuideTipView
A view  let you  highlight a special area and show a tip  

Screenshots
-----------
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20180706-170549-HD.gif)
#### use in activity
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20180706-170859.gif)
#### use in viewGroup
![demo](https://github.com/qingyc/GuideTipView/blob/master/sample/art/QQ20180706-171010.gif)

Usage
----

look here
https://www.jianshu.com/p/3206092a5751

simple use 
```
 GuideTipViewBuild(context, targetIv)
   .setGuideIvResAndLocation(R.drawable.guide_iv, locationX, locationY)
   .build()
   .show()
```
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
                            override fun onDismiss(clickDismissType: GuideTipView.ClickDismissType) {
                                Toast.makeText(this@MainActivity, "clickDismissType = $clickDismissType", Toast.LENGTH_SHORT).show()
                            }
                        })
                        //forbid auto dismiss
                        .setAutoDismiss(false)
                        //show
                        .build().show()
```

# Thanks

[fredericojssilva/ShowTipsView](https://github.com/fredericojssilva/ShowTipsView)

License
-------

    Copyright (C) 2018 qingyc
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
