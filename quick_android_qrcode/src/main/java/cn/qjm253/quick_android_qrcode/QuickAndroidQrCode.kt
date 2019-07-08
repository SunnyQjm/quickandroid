package cn.qjm253.quick_android_qrcode

import android.content.Context
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.extensions.jumpTo
import cn.qjm253.quick_android_qrcode.activity.QuickAndroidQrCodeActivity

/**
 * @author SunnyQjm
 * @date 19-7-7 下午10:11
 */

object QuickAndroidQrCode {
    fun scanQrCode(context: Context) {
        context.jumpTo(QuickAndroidQrCodeActivity::class.java)
    }
}


//////////////////////////////////////////////////////////
////////// QuickAndroid 扩展
//////////////////////////////////////////////////////////

fun QuickAndroid.scanQrCode(context: Context) {
    QuickAndroidQrCode.scanQrCode(context)
}


//////////////////////////////////////////////////////////
////////// Context 扩展
//////////////////////////////////////////////////////////

fun Context.scanCode() {
    QuickAndroidQrCode.scanQrCode(this)
}