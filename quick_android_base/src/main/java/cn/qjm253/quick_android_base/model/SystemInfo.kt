package cn.qjm253.quick_android_base.model

/**
 * @author SunnyQjm
 * @date 19-7-10 上午9:25
 */

data class SystemInfo(
    val brand: String = "",
    val model: String = "",
    val hardware: String = "",
    val sdkInt: Int = 22,
    val release: String = "",
    val statusBarHeight: Int = 0,
    val systemLanguage: String = ""
)