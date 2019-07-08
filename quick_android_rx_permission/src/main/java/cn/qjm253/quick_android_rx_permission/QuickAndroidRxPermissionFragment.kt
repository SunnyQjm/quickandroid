package cn.qjm253.quick_android_rx_permission

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import pub.devrel.easypermissions.EasyPermissions

/**
 * @author SunnyQjm
 * @date 19-7-8 上午9:13
 */

class QuickAndroidRxPermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private val permissionsMap =
        mutableMapOf<Int, ObservableEmitter<QuickAndroidRxPermissionItem>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 授权成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        permissionsMap[requestCode]?.let {
            it.onNext(QuickAndroidRxPermissionItem(true, requestCode, perms))
            it.onComplete()
            permissionsMap.remove(requestCode)
        }
    }

    /**
     * 授权失败
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        permissionsMap[requestCode]?.let {
            it.onNext(QuickAndroidRxPermissionItem(false, requestCode, perms))
            it.onComplete()
            permissionsMap.remove(requestCode)
        }
    }


    /**
     * 请求权限
     */
    fun requestPermissions(permissions: Array<String>, @StringRes tip: Int, requestCode: Int)
            : Observable<QuickAndroidRxPermissionItem> {
        return requestPermissions(permissions, getString(tip), requestCode)
    }


    fun requestPermissions(permissions: Array<String>, tip: String, requestCode: Int)
            : Observable<QuickAndroidRxPermissionItem> {
        return Observable.create { emitter ->
            permissionsMap[requestCode] = emitter
            activity?.let {
                if (EasyPermissions.hasPermissions(it, *permissions)) {
                    onPermissionsGranted(requestCode, permissions.toMutableList())
                } else {
                    EasyPermissions.requestPermissions(this, tip, requestCode, *permissions)
                }
            }
        }
    }

}