package cn.qjm253.quick_android_qrcode

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.extensions.getLazySingleton
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
    private var backIconRes: Int = R.drawable.quick_android_qr_code_default_back
    private var backIconSize: Float = 30f
    private var backIconTopMargin: Float = 15f
    private var backIconRightMargin: Float = 0f
    private var backIconBottomMargin: Float = 0f
    private var backIconLeftMargin: Float = 15f

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

    /**
     * 设置返回按钮的资源
     */
    fun setBackIconRes(@DrawableRes icon: Int): QuickAndroidQrCode {
        backIconRes = icon
        return this
    }

    /**
     * 设置返回按钮的大小
     * ps: 单位为dp
     */
    fun setBackIconSize(size: Float): QuickAndroidQrCode {
        backIconSize = size
        return this
    }

    /**
     * 设置返回按钮的边距
     * 1. 单位为dp
     * 2. 代码中已经自动去掉了状态栏的高度，不用考虑状态栏
     */
    fun setBackIconMargin(top: Float, right: Float, bottom: Float, left: Float): QuickAndroidQrCode {
        backIconTopMargin = top
        backIconRightMargin = right
        backIconBottomMargin = bottom
        backIconLeftMargin = left
        return this
    }

    /**
     * 跳转到扫码界面，并返回一个Observable对象用于接收扫码结果
     */
    fun scanQrCode(): Observable<QrCodeResult> {
        return mQrCodeTransFragment.value
            .setBackIconRes(backIconRes)
            .setBackIconSize(backIconSize)
            .setBackIconMargin(
                backIconTopMargin,
                backIconRightMargin,
                backIconBottomMargin,
                backIconLeftMargin
            )
            .scanCode(QuickAndroidQrCodeActivity::class.java)
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