package cn.qjm253.quick_android_qrcode

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.extensions.getLazySingleton
import cn.qjm253.quick_android_base.extensions.jumpTo
import cn.qjm253.quick_android_base.util.RxSchedulersHelper
import cn.qjm253.quick_android_qrcode.activity.QuickAndroidQrCodeActivity
import cn.qjm253.quick_android_qrcode.fragment.QuickAndroidQrCodeTransFragment
import cn.qjm253.quick_android_qrcode.model.QrCodeResult
import io.reactivex.Observable

/**
 * @author SunnyQjm
 * @date 19-7-7 下午10:11
 */

class QuickAndroidQrCode {

    private var mQrCodeTransFragment: Lazy<QuickAndroidQrCodeTransFragment>

    companion object {
        const val TAG = "QuickAndroidQrCode"

        fun create(activity: FragmentActivity): QuickAndroidQrCode {
            return QuickAndroidQrCode(activity)
        }

        fun create(fragment: Fragment): QuickAndroidQrCode {
            return QuickAndroidQrCode(fragment)
        }
    }

    constructor(activity: FragmentActivity) {
        mQrCodeTransFragment = activity.supportFragmentManager
            .getLazySingleton(TAG, QuickAndroidQrCodeTransFragment::class.java)
    }

    constructor(fragment: Fragment) {
        mQrCodeTransFragment = fragment.childFragmentManager
            .getLazySingleton(TAG, QuickAndroidQrCodeTransFragment::class.java)
    }

    fun scanQrCode(): Observable<QrCodeResult> {
        return mQrCodeTransFragment.value.scanCode(QuickAndroidQrCodeActivity::class.java)
            .compose(RxSchedulersHelper.io_main())
    }
}


//////////////////////////////////////////////////////////
////////// QuickAndroid 扩展
//////////////////////////////////////////////////////////

fun QuickAndroid.scanQrCode(activity: FragmentActivity): Observable<QrCodeResult> {
    return QuickAndroidQrCode.create(activity)
        .scanQrCode()
}

fun QuickAndroid.scanQrCode(fragment: Fragment): Observable<QrCodeResult> {
    return QuickAndroidQrCode.create(fragment)
        .scanQrCode()
}


//////////////////////////////////////////////////////////
////////// FragmentActivity 扩展
//////////////////////////////////////////////////////////

fun FragmentActivity.scanCode(): Observable<QrCodeResult> {
    return QuickAndroidQrCode.create(this)
        .scanQrCode()
}

//////////////////////////////////////////////////////////
////////// Fragment 扩展
//////////////////////////////////////////////////////////

fun Fragment.scanCode(): Observable<QrCodeResult> {
    return QuickAndroidQrCode.create(this)
        .scanQrCode()
}