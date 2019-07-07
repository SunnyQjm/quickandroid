package cn.qjm253.quick_android_base.extensions

import com.orhanobut.logger.Logger

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

fun String.e(vararg args: Any) {
    Logger.e(this, args)
}

fun Throwable.e(vararg args: Any) {
    Logger.e(this, this.message ?: "", args)
}


fun String.i(vararg args: Any) {
    Logger.i(this, args)
}

fun String.d(vararg args: Any) {
    Logger.d(this, args)
}

fun String.json(vararg args: Any) {
    Logger.json(this)
}