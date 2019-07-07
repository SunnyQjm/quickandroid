package cn.qjm253.quick_android_base.extensions

import cn.qjm253.quick_android_base.util.GsonUtil

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

fun Any.toJson(): String {
    return GsonUtil.bean2Json(this)
}