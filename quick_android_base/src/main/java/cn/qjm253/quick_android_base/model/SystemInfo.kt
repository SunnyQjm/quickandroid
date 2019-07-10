package cn.qjm253.quick_android_base.model

import java.util.*

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
    val systemLanguage: String = "",
    val systemLanguageList: Array<out Locale> = arrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SystemInfo

        if (brand != other.brand) return false
        if (model != other.model) return false
        if (hardware != other.hardware) return false
        if (sdkInt != other.sdkInt) return false
        if (release != other.release) return false
        if (statusBarHeight != other.statusBarHeight) return false
        if (systemLanguage != other.systemLanguage) return false
        if (!systemLanguageList.contentEquals(other.systemLanguageList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = brand.hashCode()
        result = 31 * result + model.hashCode()
        result = 31 * result + hardware.hashCode()
        result = 31 * result + sdkInt
        result = 31 * result + release.hashCode()
        result = 31 * result + statusBarHeight
        result = 31 * result + systemLanguage.hashCode()
        result = 31 * result + systemLanguageList.contentHashCode()
        return result
    }
}