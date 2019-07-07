package cn.qjm253.quick_android_base.extensions

import java.util.*

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */


fun Long.toYMD(): String {
    val instance = Calendar.getInstance()
    instance.timeInMillis = this
    return "${instance.get(Calendar.YEAR)}" +
            "-${instance.get(Calendar.MONTH) + 1}" +
            "-${instance.get(Calendar.DAY_OF_MONTH)}"
}

fun Long.toYMD_HM(): String {
    val instance = Calendar.getInstance()
    instance.timeInMillis = this
    return "${instance.get(Calendar.YEAR)}" +
            "-${instance.get(Calendar.MONTH) + 1}" +
            "-${instance.get(Calendar.DAY_OF_MONTH)}" +
            " ${instance.get(Calendar.HOUR_OF_DAY)}" +
            ":${instance.get(Calendar.MINUTE)}"
}

fun Long.toYMD_HMS(): String {
    val instance = Calendar.getInstance()
    instance.timeInMillis = this
    return "${instance.get(Calendar.YEAR)}" +
            "-${instance.get(Calendar.MONTH) + 1}" +
            "-${instance.get(Calendar.DAY_OF_MONTH)}" +
            " ${instance.get(Calendar.HOUR_OF_DAY)}" +
            ":${instance.get(Calendar.MINUTE)}" +
            ":${instance.get(Calendar.SECOND)}"
}

fun String.toTimeStamp(): Long {
    val arr = this.split('-')
    val instance = Calendar.getInstance()
    instance.timeInMillis = 0
    instance.set(Calendar.YEAR, arr[0].toInt())
    instance.set(Calendar.MONTH, arr[1].toInt() - 1)
    instance.set(Calendar.DAY_OF_MONTH, arr[2].toInt())
    return instance.timeInMillis
}