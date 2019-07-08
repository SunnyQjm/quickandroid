package cn.qjm253.quick_android_rx_permission

/**
 * @author SunnyQjm
 * @date 19-7-8 上午9:34
 */

class QuickAndroidRxPermissionItem(
    val granted: Boolean,
    val requestCode: Int,
    val perms: MutableList<String>
)