package cn.qjm253.quick_android_custom_view.other

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes


/**
 * @author SunnyQjm
 * @date 19-7-14 下午8:13
 */
interface LineMenu {
    fun getLeft_icon(): ImageView?

    fun getRight_icon(): ImageView?

    fun getTv_title(): TextView?

    fun getTv_value(): TextView?

    fun setLeftIcon(@DrawableRes res: Int)

    fun setRightIcon(@DrawableRes res: Int)


    fun setRightIconVisible(visible: Int)

    fun setLeftIconVisible(visible: Int)

    fun setTitleIconVisible(visible: Int)

    fun setValueIconVisible(visible: Int)

    fun setTitle(title: String)

    fun setValue(value: String)
}