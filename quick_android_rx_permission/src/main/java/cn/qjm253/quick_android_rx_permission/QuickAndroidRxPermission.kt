package cn.qjm253.quick_android_rx_permission

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import cn.qjm253.quick_android_base.QuickAndroid
import io.reactivex.Observable

/**
 * @author SunnyQjm
 * @date 19-7-8 上午9:03
 */

class QuickAndroidRxPermission {
    companion object {
        const val TAG = "QuickAndroidRxPermission"
    }

    private var mRxPermissionFragment: Lazy<QuickAndroidRxPermissionFragment>
    private var requestCode = 0

    constructor(activity: FragmentActivity) {
        mRxPermissionFragment = getLazySingleton(activity.supportFragmentManager)
    }

    constructor(fragment: Fragment) {
        mRxPermissionFragment = getLazySingleton(fragment.childFragmentManager)
    }

    private fun getLazySingleton(supportFragmentManager: FragmentManager): Lazy<QuickAndroidRxPermissionFragment> {
        return object : Lazy<QuickAndroidRxPermissionFragment> {
            private var rxPermissionFragment: QuickAndroidRxPermissionFragment? = null

            private fun getFragment(): QuickAndroidRxPermissionFragment {
                if (rxPermissionFragment == null) {
                    rxPermissionFragment = getRxPermissionsFragment(supportFragmentManager)
                }
                return rxPermissionFragment!!
            }

            override val value: QuickAndroidRxPermissionFragment
                get() = getFragment()

            override fun isInitialized(): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

    private fun getRxPermissionsFragment(fragmentManager: FragmentManager): QuickAndroidRxPermissionFragment {
        var rxPermissionFragment = findRxPermissionsFragment(fragmentManager)
        if (rxPermissionFragment == null) {
            rxPermissionFragment = QuickAndroidRxPermissionFragment()
            fragmentManager
                .beginTransaction()
                .add(rxPermissionFragment, TAG)
                .commitNow()
        }
        return rxPermissionFragment
    }

    private fun findRxPermissionsFragment(fragmentManager: FragmentManager): QuickAndroidRxPermissionFragment? {
        return fragmentManager.findFragmentByTag(TAG) as? QuickAndroidRxPermissionFragment
    }


    fun request(permissions: Array<String>, @StringRes tip: Int): Observable<QuickAndroidRxPermissionItem> {
        requestCode++
        return mRxPermissionFragment.value.requestPermissions(permissions, tip, requestCode)
    }

    fun request(permissions: Array<String>, tip: String = "请求权限"): Observable<QuickAndroidRxPermissionItem> {
        requestCode++
        return mRxPermissionFragment.value.requestPermissions(permissions, tip, requestCode)
    }
}

//////////////////////////////////////////////////////////
////////// QuickAndroid 扩展
//////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////
////////// Context 扩展
//////////////////////////////////////////////////////////