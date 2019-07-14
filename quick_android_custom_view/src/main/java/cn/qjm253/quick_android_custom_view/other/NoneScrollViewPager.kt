package cn.qjm253.quick_android_custom_view.other

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent



/**
 * @author SunnyQjm
 * @date 19-7-14 下午7:52
 */

class NoneScrollViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {
    private var isScroll = false
    init {

    }

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        return if (isScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        return if (isScroll) {
            super.onTouchEvent(ev)
        } else {
            true// 可行,消费,拦截事件
        }
    }

    fun setScroll(scroll: Boolean) {
        isScroll = scroll
    }
}