package cn.qjm253.quick_android_base.extensions

import cn.qjm253.quick_android_base.util.GsonUtil

fun Any.toJson(): String {
    return GsonUtil.bean2Json(this)
}