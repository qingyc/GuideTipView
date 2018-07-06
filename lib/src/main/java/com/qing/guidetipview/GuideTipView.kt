package com.qing.guidetipview

import android.R
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * 类说明: 新手引导
 *
 *  指定一个控件高亮,其余区域半透明,可以添加引导图片或者文字
 *
 * @author qing
 * @time 2018/5/28 11:47
 *
 * @param mContext         上下文
 * @param targetView       设置的目标控件
 */
class GuideTipView(private val mContext: Context, private val targetView: View) : RelativeLayout(mContext) {

    /**
     * : test 用设置引导是否只显示一次  mIsDebug 显示多次 ; 默认false只显示一次
     */
    var mIsDebug = false
    //高亮控件的中心点(相对外层容器 不一定是屏幕)
    private var mHighlightAreaCenterPoint = Point()
    //高亮的半径
    private var mHighlightAreaRadius = 0
    // 屏幕尺寸
    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0
    //半透明背景颜色
    private var bgColor: Int = Color.parseColor("#66000000")
    //================= 指引图片 =============
    //指引图片
    private var mGuideIv = ImageView(context)
    //指引图片和高亮目标距离
    var mGuideIvDiffY = 0
    var mGuideIvDiffX = 0
    //引导图片的资源
    var mGuideIvRes: Int = 0
    //================= 指引文字 =============
    //指引文字
    var mGuideTv: TextView = LayoutInflater.from(context).inflate(com.qing.guidetipview.R.layout.tip_text, null, false) as TextView
    //指引文字和高亮目标距离
    var mGuideTvDiffY = 0
    var mGuideTvDiffX = 0
    //是否显示指引文字
    var mShowGuideTv = false
    //被高亮了的背景bitmap
    private var mBgBitmap: Bitmap? = null
    //容器画板 用于生成带高亮区域的背景bitmap
    private var mTempCanvas: Canvas? = null
    //背景画笔
    private var mBgPaint: Paint = Paint()
    private var isMeasured: Boolean = false
    //获取的目标控件的位置
    private var mTargetViewLeft: Int = 0
    private var mTargetViewTop: Int = 0
    // 是否已经显示过了
    private var mHasShow: Boolean = false
    //是否自动消失
    var mAutoDismiss = true
    //自动消失时间
    private var mDismissDelayTime: Long = 4000L //默认4秒消失
    //点击消失的类型 
    var mClickClickDismissType: ClickDismissType = ClickDismissType.DISMISS_IN_HIGH_LIGHT_AREA
    //蒙版区域(指定不是整个Activity显示引导)
    var mContainerForGuide: ViewGroup? = null
    //显示圆形高亮区
    var mShowCircleGuide = true
    //是否高亮边缘点划线
    var mShowDashPath = true
    //消失监听
    var mDismissListener: DismissListener? = null
    //引导图片的动画
    var mGuideIvAnimator: Animator? = null

    //设置高亮区点划线
    var mDashStrokeWidth = 5f
    var mDashWidth = 20f
    var mDashGap = 10f
    var mDashOffset = mDashStrokeWidth
    //bg alpha
    var mBgAlpha=150

    // 控件显示状态存储SP的ID
    private var mGuideInInSp = ""

    companion object {
        const val SHOW_GUIDE = 101
        const val DISMISS_GUIDE = 102
        //SP中保存是否已经显示
        const val SP_NAME = "GUIDE_VIEWS"
        //SP前缀
        const val SP_NAME_ID_PREFIX = "GUIDE_VIEW_KEY"

        /**
         * 检测是否已经显示过了
         * @return ture 标识没有显示过改控件的引导
         */
        fun hasNotShowThisGuide(context: Context, id: Int): Boolean {
            val guideId = "$id"
            return if (TextUtils.isEmpty(guideId)) {
                false
            } else {
                !context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getBoolean("$SP_NAME_ID_PREFIX$guideId", false)
            }
        }

        //防止测试时多次点击添加
        var nowShowingTargetId = -1
    }

    /**
     * 显示和移除提示的handler
     */
    private var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg == null) {
                return
            }
            when (msg.what) {
            //显示新手引导
                SHOW_GUIDE -> {
                    showGuide()
                    //默认自动消失
                    if (mAutoDismiss) {
                        val obtain = Message.obtain()
                        obtain.what = DISMISS_GUIDE
                        sendMessageDelayed(obtain, mDismissDelayTime)
                    }
                    if (BuildConfig.DEBUG) {
                        Log.i("GuideTipView", "SHOW_GUIDE==== $mGuideInInSp   ===$mDismissDelayTime")
                    }
                }
            //移除新手引导
                DISMISS_GUIDE -> {
                    if (BuildConfig.DEBUG) {
                        Log.i("GuideTipView", "DISMISS_GUIDE==== $mGuideInInSp")
                    }
                    dismiss(ClickDismissType.AUTO_DISMISS)
                }
            }
        }
    }


    /**
     * 点击哪个区域可以消失
     */
    enum class ClickDismissType {
        //点击高亮消失
        DISMISS_IN_HIGH_LIGHT_AREA,
        //点击指引图片消失
        DISMISS_IN_GUIDE_IV,
        //点击指引文字消失
        DISMISS_IN_GUIDE_TV,
        //点击所有区域消失
        DISMISS_IN_ALL_AREA,
        //自动消失
        AUTO_DISMISS
    }

    init {
        this.visibility = View.GONE
        this.setBackgroundColor(Color.TRANSPARENT)
        mGuideInInSp = "${targetView.id}"
        // QTIP: 2018/5/28  设置Z方向位置,避免被系统状态栏遮住
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = 10F
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mScreenWidth = w
        mScreenHeight = h
    }


    /**
     * 添加指引图片
     */
    private fun addGuideIv() {
        //设置要显示的引导图片
        mGuideIv.setImageDrawable(ContextCompat.getDrawable(context, mGuideIvRes))
        val guideBitmap = BitmapFactory.decodeResource(resources, mGuideIvRes)
        guideBitmap?.let {
            //qtip 设置 引导图片显示的位置参数
            val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            val topMargin = mHighlightAreaCenterPoint.y - targetView.height / 2 + if (mGuideIvDiffY == 0) targetView.height + 50 else mGuideIvDiffY
            params.topMargin = topMargin
            //Log.e(TAG, "params.topMargin===" + params.topMargin);
            // 判断设置图片在左边还是右边显示
            if (mTargetViewLeft < mScreenWidth / 4) {// 在目标右边设置guideView
                params.leftMargin = mHighlightAreaCenterPoint.x - targetView.width / 2 + if (mGuideIvDiffX == 0) 2 * targetView.width / 3 else mGuideIvDiffX
                //	Log.e(TAG, "params.leftMargin===" + params.leftMargin);
            } else {// 在目标左边设置guideView
                params.leftMargin = mHighlightAreaCenterPoint.x - targetView.width / 2 + if (mGuideIvDiffX == 0) -it.width + targetView.width / 3 else mGuideIvDiffX
                //	Log.e(TAG, "在目标左边设置guideView------params.leftMargin===" + params.leftMargin);
            }
            mGuideIv.layoutParams = params
            //this.removeAllViews()
            //qtip 添加显示引导图片
            this.addView(mGuideIv)
        }
        // ===========    初始化指引图片  end  ================ //
    }


    private fun addGuideTv() {
        if (!mShowGuideTv) {
            return
        }
        val diffX = mGuideTvDiffX
        val diffY = mGuideTvDiffY
        val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        params.topMargin = mHighlightAreaCenterPoint.y - targetView.height / 2 + if (diffY == 0) targetView.height + 50 else diffY
        // 判断设置图片在左边还是右边显示
        if (mTargetViewLeft < mScreenWidth / 4) {// 在目标右边设置guideView
            params.leftMargin = mHighlightAreaCenterPoint.x - targetView.width / 2 + if (diffX == 0) 2 * targetView.width / 3 else diffX
        } else {// 在目标左边设置guideView
            params.leftMargin = mHighlightAreaCenterPoint.x - targetView.width / 2 + if (diffX == 0) -mGuideTv!!.width + targetView.width / 3 else diffX
        }
        mGuideTv.layoutParams = params
        this.addView(mGuideTv)
    }


    /**
     * 获取需要高亮的控件相对于屏幕或者外层容器的left和top
     */
    private fun getTargetViewLocation() {
        // QTIP: 2018/5/29  如果是在activity中 获取的是在屏幕上的位置 ,如果是在指定父容器中显示获取的是在父容器中的位置
        if (mContainerForGuide == null) {
            val location = IntArray(2)
            targetView.getLocationOnScreen(location)
            mTargetViewLeft = location[0]
            mTargetViewTop = location[1]
        } else {
            mTargetViewLeft = targetView.left
            mTargetViewTop = targetView.top
        }
    }


    /**
     * 显示引导
     */
    fun show(showDelayTime: Long = 500L, dismissDelayTime: Long = -1) {

        if (nowShowingTargetId == targetView.id) {
            return
        }
        nowShowingTargetId = targetView.id
        if (dismissDelayTime != -1L) {
            this.mDismissDelayTime = dismissDelayTime
        }
        mGuideIvAnimator?.let {
            it.setTarget(mGuideIv)
            it.start()
        }
        mHandler.sendEmptyMessageDelayed(SHOW_GUIDE, showDelayTime)
    }

    /**
     * 显示新手引导
     */
    private fun showGuide() {
        //提示显示的布局
        val rootViewShowGuide: ViewGroup? = mContainerForGuide
                ?: if (mContext is Activity) {
                    mContext.window.decorView as ViewGroup
                } else null
        rootViewShowGuide?.let {
            // 判断是否已经显示过
            mHasShow = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getBoolean("$SP_NAME_ID_PREFIX$mGuideInInSp", false)
            //已经显示过了就点击消失
            if (!mIsDebug && mHasShow) {
                visibility = View.GONE
                try {
                    it.removeView(this@GuideTipView)
                } catch (e: Exception) {
                }
                return
            }

            // 点击后存储显示与否的状态
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            sp.edit().putBoolean("$SP_NAME_ID_PREFIX$mGuideInInSp", true).apply()

            it.removeView(this@GuideTipView)
            // QTIP: 2018/5/29  LayoutParams MATCH_PARENT
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.addView(this@GuideTipView, params)
            this@GuideTipView.visibility = View.VISIBLE
            val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            this@GuideTipView.startAnimation(fadeInAnimation)

            targetView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (isMeasured) return
                    if (targetView.height > 0 && targetView.width > 0) {
                        isMeasured = true
                    }
                    getTargetViewLocation()
                    val centerX: Int = mTargetViewLeft + targetView.width / 2
                    val centerY: Int = mTargetViewTop + targetView.height / 2
                    mHighlightAreaRadius = targetView.width / 2
                    mHighlightAreaCenterPoint.x = centerX
                    mHighlightAreaCenterPoint.y = centerY
                    addGuideIv()
                    addGuideTv()
                    invalidate()
                    targetView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }


    /**
     * 移除新手引导
     */
    private fun dismiss(clickDismissType: GuideTipView.ClickDismissType) {
        mHandler.removeCallbacksAndMessages(null)
        mDismissListener?.onDismiss(clickDismissType)
        try {
            (parent as ViewGroup).removeView(this)
        } catch (e: Exception) {

        }
        nowShowingTargetId = -1
    }


    /**
     * 监听触摸
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                val downX = event.x
                val downY = event.y
                //是够在可点击区
                var inClickArea = true

                when (mClickClickDismissType) {
                //点击所有区域消失
                    ClickDismissType.DISMISS_IN_ALL_AREA -> {
                        inClickArea = true
                    }
                //店家高亮区消失
                    ClickDismissType.DISMISS_IN_HIGH_LIGHT_AREA -> {
                        //判断点击区域是否在高亮区域
                        val inClickAreaWidth = downX < mHighlightAreaCenterPoint.x + targetView.width / 2 && downX > mHighlightAreaCenterPoint.x - targetView.width / 2
                        val inClickAreaHeight = downY < mHighlightAreaCenterPoint.y + targetView.height / 2 && downY > mHighlightAreaCenterPoint.y - targetView.height / 2
                        inClickArea = inClickAreaWidth && inClickAreaHeight

                    }
                //点击图片消失
                    ClickDismissType.DISMISS_IN_GUIDE_IV -> {
                        //判断是否点击引导图片
                        val inClickAreaWidth = downX < mGuideIv.right && downX > mGuideIv.left
                        val inClickAreaHeight = downY < mGuideIv.bottom && downY > mGuideIv.top
                        inClickArea = inClickAreaWidth && inClickAreaHeight

                    }
                //点击文字消失
                    ClickDismissType.DISMISS_IN_GUIDE_TV -> {
                        //判断是否点击引导文字
                        val inClickAreaWidth = downX < mGuideTv.right && downX > mGuideTv.left
                        val inClickAreaHeight = downY < mGuideTv.bottom && downY > mGuideTv.top
                        inClickArea = inClickAreaWidth && inClickAreaHeight
                    }
                //DISMISS_FORBID_CLICK
                    else -> {
                        inClickArea = false
                    }
                }

                if (inClickArea) {
                    dismiss(mClickClickDismissType)
                }

            }
        }
        return true
    }


    /**
     * 绘制
     */
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (width <= 0 || height <= 0) {
            return
        }
        //1 绘制半透明背景
        // 设置透明背景
        if (mBgBitmap == null) {
            mBgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            mTempCanvas = Canvas(mBgBitmap)
        }
        mBgPaint.color = Color.parseColor("#000000")
        // mBgPaint.color = bgColor
        mBgPaint.alpha = mBgAlpha
        mTempCanvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mBgPaint)


        //2.绘制高亮区 // QTIP: 2018/5/28 从temp 上去掉了高亮
        mBgPaint.reset()
        val transparentPaint = Paint()
        transparentPaint.color = ContextCompat.getColor(mContext, R.color.transparent)
        transparentPaint.isAntiAlias = true
        transparentPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        //高亮目标控件的中心位置
        val centerX = mHighlightAreaCenterPoint.x
        val centerY = mHighlightAreaCenterPoint.y

        //2.1显示圆形高亮
        if (mShowCircleGuide) {
            mTempCanvas!!.drawCircle(centerX.toFloat(), centerY.toFloat(), maxOf(targetView.height / 2, targetView.width / 2).toFloat() + 10, transparentPaint)
            canvas.drawBitmap(mBgBitmap, 0f, 0f, mBgPaint)
        }
        //2.2显示矩形高亮
        else {
            //当控件尺寸小于2*150时 调整高亮区 避免高亮区太小
            if (mHighlightAreaRadius < 150) {
                val rect = RectF((centerX - mHighlightAreaRadius).toFloat(), (centerY - targetView.height / 2).toFloat(), (centerX + mHighlightAreaRadius).toFloat(), (centerY + targetView.height / 2).toFloat())
                mTempCanvas!!.drawRoundRect(rect, 15.0f, 15.0f, transparentPaint)
            } else {
                val rect = RectF((centerX - mHighlightAreaRadius + 8).toFloat(), (centerY - targetView.height / 2 + 5).toFloat(), (centerX + mHighlightAreaRadius - 8).toFloat(), (centerY + targetView.height / 2 - 5).toFloat())
                mTempCanvas!!.drawRoundRect(rect, 15.0f, 15.0f, transparentPaint)

            }
            canvas.drawBitmap(mBgBitmap, 0f, 0f, mBgPaint)
        }

        //绘制高亮区域外边的点画线
        if (mShowDashPath) {
            //设置点划线画笔
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            p.style = Paint.Style.STROKE
            p.color = Color.WHITE
            p.strokeWidth = mDashStrokeWidth
            val effects = DashPathEffect(floatArrayOf(mDashWidth, mDashGap), 1f)
            p.pathEffect = effects

            if (mShowCircleGuide) {
                canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), maxOf(targetView.height / 2, targetView.width / 2).toFloat() + 10 + mDashOffset, p)
            } else {
                //当控件小于2*150时
                if (mHighlightAreaRadius < 150) {
                    val rect2 = RectF((centerX - mHighlightAreaRadius - 4).toFloat(), (centerY - targetView.height / 2 - 4).toFloat(), (centerX + mHighlightAreaRadius + 4).toFloat(), (centerY + targetView.height / 2 + 4).toFloat())
                    canvas.drawRoundRect(rect2, 15.0f, 15.0f, p) //画空心圆角矩形
                }
                //当控件大于2*150时
                else {
                    val rect2 = RectF((centerX - mHighlightAreaRadius + 3).toFloat(), (centerY - targetView.height / 2).toFloat(), (centerX + mHighlightAreaRadius - 3).toFloat(), (centerY + targetView.height / 2).toFloat())
                    canvas.drawRoundRect(rect2, 15.0f, 15.0f, p)
                }
            }
        }
    }

}
