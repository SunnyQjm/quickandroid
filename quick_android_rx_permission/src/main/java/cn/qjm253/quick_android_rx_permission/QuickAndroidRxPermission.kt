package cn.qjm253.quick_android_rx_permission

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.qjm253.quick_android_base.extensions.getLazySingleton
import cn.qjm253.quick_android_base.util.RxSchedulersHelper
import io.reactivex.Observable

/**
 * @author SunnyQjm
 * @date 19-7-8 上午9:03
 */

class QuickAndroidRxPermission {
    companion object {
        const val TAG = "QuickAndroidRxPermission"

        fun create(activity: FragmentActivity): QuickAndroidRxPermission {
            return QuickAndroidRxPermission(activity)
        }

        fun create(fragment: Fragment): QuickAndroidRxPermission {
            return QuickAndroidRxPermission(fragment)
        }
    }

    private var mRxPermissionFragment: Lazy<QuickAndroidRxPermissionFragment>
    private var requestCode = 0

    constructor(activity: FragmentActivity) {
        mRxPermissionFragment = activity.supportFragmentManager
            .getLazySingleton(TAG, QuickAndroidRxPermissionFragment::class.java)
    }

    constructor(fragment: Fragment) {
        mRxPermissionFragment = fragment.childFragmentManager
            .getLazySingleton(TAG, QuickAndroidRxPermissionFragment::class.java)
    }


    fun request(permissions: Array<String>, @StringRes tip: Int): Observable<QuickAndroidRxPermissionItem> {
        requestCode++
        return mRxPermissionFragment.value.requestPermissions(permissions, tip, requestCode)
            .compose(RxSchedulersHelper.io_main())
    }

    fun request(permissions: Array<String>, tip: String = "请求权限"): Observable<QuickAndroidRxPermissionItem> {
        requestCode++
        return mRxPermissionFragment.value.requestPermissions(permissions, tip, requestCode)
            .compose(RxSchedulersHelper.io_main())
    }
}

//////////////////////////////////////////////////////////
////////// QuickAndroid 扩展
//////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////
////////// Context 扩展
//////////////////////////////////////////////////////////