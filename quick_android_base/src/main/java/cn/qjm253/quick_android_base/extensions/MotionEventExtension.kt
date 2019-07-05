package cn.qjm253.quick_android_base.extensions

import android.view.MotionEvent
import android.view.View

/**
 *
 * Created by sunny on 18-1-2.
 */

/**
 * 该函数用来判断一个触摸事件是否发生在某个具体View的范围内
 */
fun MotionEvent.isInView(view: View): Boolean {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    val x = location[0]
    val y = location[1]
    return !(this.x < x || this.x > x + view.width || this.y < y || this.y > y + view.height)
}
