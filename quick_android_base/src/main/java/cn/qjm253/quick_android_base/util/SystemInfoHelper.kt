package cn.qjm253.quick_android_base.util

import android.content.Context
import cn.qjm253.quick_android_base.model.SystemInfo
import java.util.*

/**
 * 获取系统信息的工具类
 *
 * @author SunnyQjm
 * @date 19-7-10 上午9:02
 */

object SystemInfoHelper {

    /**
     * 获取系统状态栏的高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获取手机型号
     */
    fun getSystemModel(): String {
        return android.os.Build.MODEL ?: ""
    }

    /**
     * 获取手机厂商
     */
    fun getDeviceBrand(): String {
        return android.os.Build.BRAND ?: ""
    }

    /**
     * 获取手机的版本号
     */
    fun getSystemVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 获取当前手机的语言
     */
    fun getSystemLanguage(): String {
        return Locale.getDefault().language ?: ""
    }

    /**
     * 获取当前系统上的语言列表
     */
    fun getSystemLanguageLists(): Array<out Locale> {
        return Locale.getAvailableLocales() ?: arrayOf()
    }

    /**
     * 获取系统硬件信息
     */
    fun getSystemHardware(): String {
        return android.os.Build.HARDWARE ?: ""
    }

    /**
     * 获取当前系统的SDK版本
     */
    fun getSdkInt(): Int {
        return android.os.Build.VERSION.SDK_INT
    }


    /**
     * 构建系统信息
     */
    fun buildSystemInfo(context: Context): SystemInfo {
        return SystemInfo(
            getDeviceBrand(),
            getSystemModel(),
            getSystemHardware(),
            getSdkInt(),
            getSystemVersion(),
            getStatusBarHeight(context),
            getSystemLanguage(),
            getSystemLanguageLists()
        )
    }
}