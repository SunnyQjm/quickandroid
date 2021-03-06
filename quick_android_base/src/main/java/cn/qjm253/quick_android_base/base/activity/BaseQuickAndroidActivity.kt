package cn.qjm253.quick_android_base.base.activity

import android.net.Uri
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import com.github.anzewei.parallaxbacklayout.ParallaxBack
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout
import pub.devrel.easypermissions.EasyPermissions

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
@ParallaxBack
abstract class BaseQuickAndroidActivity : AppCompatActivity(),
    EasyPermissions.PermissionCallbacks, BaseQuickAndroidFragment.OnFragmentInteractionListener {

    companion object {
        const val EP_CAMERA = 1
    }

    /**
     * 是否需要支持侧滑返回
     */
    open fun isNeedScrollBack() = true


    /**
     * 侧滑是否响应全屏
     * true => 全屏滑动都会触发侧滑
     * false => 边缘滑动才会触发侧滑
     */
    open fun isParallaxBackLayoutFullScreen() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (QuickAndroid.enableParallaxBack) {      // 手动开启侧滑才执行侧滑相关代码
            ParallaxHelper.getParallaxBackLayout(this, true)?.let { layout ->
                // 设置侧滑的响应模式
                if (isParallaxBackLayoutFullScreen()) {
                    layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL)
                } else {
                    layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT)
                }

                // 设置是否允许触发侧滑返回
                if (isNeedScrollBack()) {
                    layout.setEnableGesture(true)
                } else {
                    layout.setEnableGesture(false)
                }
            }
        }
    }

    /**
     * 禁止侧滑返回
     */
    fun disableParallaxBack() {
        if(QuickAndroid.enableParallaxBack) {
            ParallaxHelper.getParallaxBackLayout(this)
                ?.setEnableGesture(false)
        }
    }

    /**
     * 开启侧滑返回
     */
    fun enableParallaxBack() {
        if(QuickAndroid.enableParallaxBack){
            ParallaxHelper.getParallaxBackLayout(this)
                ?.setEnableGesture(true)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 授权成功
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    /**
     * 授权失败
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }


    open fun easyRequestPermissions(permissions: Array<String>, @StringRes tip: Int, requestCode: Int) {
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            onPermissionsGranted(requestCode, permissions.toMutableList())
        } else {
            EasyPermissions.requestPermissions(this, getString(tip), requestCode, *permissions)
        }
    }

    override fun onFragmentInteraction(uri: Uri?) {
    }
}