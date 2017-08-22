package cn.qssq666.banner

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import cn.qssq666.kotlin.banner.R
import kotlinx.android.synthetic.main.view_auto_scroll_layout.view.*


class Banner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    internal var geSturelistener: OnGestureListener = object : OnGestureListener {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (onItemListener != null) {
                onItemListener!!.onClick(lastPosition)
            }
            return false
        }

        override fun onShowPress(e: MotionEvent) {}

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {

            return false
        }

        override fun onLongPress(e: MotionEvent) {}

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

            return false
        }

        override fun onDown(e: MotionEvent): Boolean {

            return false
        }
    }


    fun init() {
        View.inflate(context, getlayout(), this)
        titleView = banner_title;
        titleGroup = banner_title_bg;
        pointViewGroup = banner_point_container;
        viewPager = banner_viewpager;
        viewPager!!.adapter = mAdapter
        viewPager!!.addOnPageChangeListener(mPagelistener)
/*
        pointViewGroup = findViewById(R.id.banner_point_container) as LinearLayout
        viewPager = findViewById(R.id.banner_viewpager) as ViewPager
        viewPager!!.adapter = mAdapter
        viewPager!!.addOnPageChangeListener(mPagelistener)
        viewPager!!.setOnTouchListener(mTouchlistener)
*/
        detector = GestureDetector(context, geSturelistener)
        viewPager!!.setOnTouchListener(mTouchlistener)
    }


    var titleGroup: ViewGroup? = null
        private set

    /**
     * 在初始化init里面调用的。
     */
    var viewPager: ViewPager? = null
        private set

    fun getInternalViewPager(): ViewPager? {
        return viewPager;
    }

    /**
     * 单位 DP
     *
     * @param pointSize
     */
    fun setPointSize(pointSize: Int) {
        checkSetException()
        this.mPointSize = pointSize
    }

    private var mPointSize = 7


    /**
     * 单位 dp 电和点之间的距离。
     *
     * @param margin
     */
    fun setPointMargin(margin: Int) {
        checkSetException()
        this.mPointMargin = margin
    }

    private fun checkSetException() {
        if (pointViewGroup!!.childCount > 0) {
            Log.w(TAG, "请再setItem之前调用本方法,因为当前已经初始化了,指示点count:" + pointViewGroup!!.childCount + " 或者调用clearPointLayout方法")
            //            throw new RuntimeException("请再setItem之前调用本方法,因为当前已经初始化了指示点count:" + mPointContainer.getChildCount() + " 或者调用clearPointLayout方法");
        }
    }

    fun clearPointLayout() {
        pointViewGroup!!.removeAllViews()
    }

    private var mPointMargin = 10

    //	Enum<Enum<E>>;
    var titleView: TextView? = null
        private set


    var pointViewGroup: LinearLayout? = null
        private set
    private var mItems: List<IImgInfo>? = null

    /*   */
    /**
     * 先清除所有的 然后根据是否需要继续
     */
    /*
        stopAutoScroll();
//		mHandler.removeCallbacksAndMessages(null);
        if (this.mAutoScroll) {
            startAutoScroll();
        }*/
    var autoScroll: Boolean = false
            ;
    var isAutoScrolling: Boolean = false
        private set


    fun setNeedPoint(needPoint: Boolean) {
        if (pointViewGroup != null) {
            pointViewGroup!!.visibility = if (needPoint) View.VISIBLE else View.GONE
        }
    }

    fun setNeedTitle(needTitle: Boolean) {
        if (titleGroup != null) {
            titleGroup!!.visibility = if (needTitle) View.VISIBLE else View.GONE

        }
    }


    /**
     * 设置时间
     *
     * @param time
     */
    fun setDuration(time: Long) {
        scrollTime = time
    }


    open fun getlayout() = R.layout.view_auto_scroll_layout;

    /**
     * 开始滚动
     */
    fun startAutoScroll() {
        if (mItems == null) {
            Log.d(TAG, "不能开始startAutoScroll因为item数据为空")
            return
        }
        isAutoScrolling = true
        removeCallbacks(mScrollRunnable)
        postDelayed(mScrollRunnable, scrollTime)
    }


    /**
     * 临时停止滚动 并不会改变状态
     */
    fun stopAutoScroll() {
        Log.d(TAG, "stopAutoScroll")
        removeCallbacks(mScrollRunnable)
        isAutoScrolling = false
    }

    var mScrollRunnable: Runnable = object : Runnable {
        override fun run() {
            onDoAutoScrollLogic()
            //            currentItem = currentItem == mItems.size() && (isStart = true) ? 0 : currentItem;//如果是是最后一个那么就是开始了，那么就会真
            postDelayed(this, scrollTime)//递归执行
        }
    }

    protected fun onDoAutoScrollLogic() {
        val currentItem = viewPager!!.currentItem
        val isStart = false
        if (isForward) {
            if (currentItem >= mItems!!.size - 1) {
                isForward = false
                viewPager!!.setCurrentItem(currentItem - 1, true)

            } else {
                viewPager!!.setCurrentItem(currentItem + 1, true)
            }

        } else {
            if (currentItem == 0) {
                viewPager!!.setCurrentItem(currentItem + 1, true)
                isForward = true
            } else {
                viewPager!!.setCurrentItem(currentItem - 1, true)
            }
        }
    }

    private var isForward = true

    /**
     * 这句话之后了才能进行操作
     *
     * @param listImgInfo
     */
    fun setItem(listImgInfo: List<IImgInfo>?) {
        if (listImgInfo == null) {
            Log.d(TAG, "imgInfo为空")
            return
        }
        this.mItems = listImgInfo
        pointViewGroup!!.removeAllViews()//防止多次设置产生更多的圆点
        val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPointSize.toFloat(), resources.displayMetrics).toInt()
        val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPointMargin.toFloat(), resources.displayMetrics).toInt()
        for (info in listImgInfo) {
            val view = onCreatePointView(info, size, margin)
            pointViewGroup!!.addView(view)// 添加点
        }
        mAdapter.notifyDataSetChanged()
        mPagelistener.onPageSelected(0)
    }

    protected fun onCreatePointView(info: IImgInfo, size: Int, margin: Int): View {
        val viewPoint = View(context)
        viewPoint.setBackgroundResource(R.drawable.dot)
        val value = TypedValue()
        // value.complexToDimensionPixelOffset(data, metrics)
        // 把像素转换为点
        val params = LinearLayout.LayoutParams(size, size)
        params.leftMargin = margin
        viewPoint.layoutParams = params
        return viewPoint
    }

    interface IImgInfo {
        open val bannerTitle: String?

    }


    /**
     * 指示点被选中
     *
     * @param position
     */
    protected fun onSelect(position: Int) {
        pointViewGroup!!.getChildAt(position).isEnabled = false
    }


    protected fun onCancelSelect(position: Int) {
        pointViewGroup!!.getChildAt(position).isEnabled = true
    }

    var lastPosition = 0
    private val mPagelistener = object : OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            // 被选中的是

            onCancelSelect(lastPosition)
            titleView!!.text = mItems!![position].bannerTitle
            onSelect(position)
            lastPosition = position
            Log.w(TAG, "当前位置:" + position + "," + mItems!![position].bannerTitle)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

        override fun onPageScrollStateChanged(arg0: Int) {}
    }
    private val mAdapter = object : PagerAdapter() {

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {

            return arg0 === arg1
        }

        override fun getCount(): Int {

            return if (mItems == null) 0 else mItems!!.size
            // return items.size();//刚开始没设置所以是空指针的
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

            // super.destroyItem(container, position, object);
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val model = mItems!![position]
            val view = bindHolderProvider!!.onCreateView(container, model, position)
            container.addView(view)
            return view
        }

    }


    internal var mTouchlistener: View.OnTouchListener = View.OnTouchListener { v, event ->
        detector!!.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (autoScroll)
            //如果在滚动就停止滚动
            {
                stopAutoScroll()
            }
            MotionEvent.ACTION_UP -> if (autoScroll)
            //如果本来是滚动状态那么你该恢复滚动了
            {
                startAutoScroll()
            }
            else -> {
            }
        }
        false
    }


    private var detector: GestureDetector? = null
    private var onItemListener: OnItemClickListener? = null

    /**
     * 监听点击事件
     *
     * @param onItemListener
     */
    fun setOnItemClickListener(onItemListener: OnItemClickListener) {
        this.onItemListener = onItemListener
    }

    interface OnItemClickListener {

        fun onClick(index: Int)
    }

    interface OnViewBindHolderProvider<in t> {
        fun onCreateView(group: ViewGroup, model: t, position: Int): View
    }


    fun setBindHolderProvider(bindHolderProvider: OnViewBindHolderProvider<in IImgInfo>) {
        this.bindHolderProvider = bindHolderProvider
    }

    internal var bindHolderProvider: OnViewBindHolderProvider<in IImgInfo>? = null


    var scrollTime: Long = 1000

    companion object {
        private val TAG = "Banner"


    }

    init {
        init()
        //		Toast.makeText(context, text, duration)
    }


}
