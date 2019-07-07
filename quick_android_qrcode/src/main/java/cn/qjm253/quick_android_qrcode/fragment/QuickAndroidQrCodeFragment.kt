package cn.qjm253.quick_android_qrcode.fragment

import android.content.Intent
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.extensions.vibrator
import cn.qjm253.quick_android_base.params.IntentParam
import cn.qjm253.quick_android_qrcode.R
import kotlinx.android.synthetic.main.fragment_quick_android_qrcode.*


/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

class QuickAndroidQrCodeFragment : BaseQuickAndroidFragment() {

    companion object {
        const val QR_CODE_RESULT = "QR_CODE_RESULT"
        const val QR_CODE_ERROR = "QR_CODE_ERROR"

        fun newInstance(intentParam: IntentParam? = null): QuickAndroidQrCodeFragment {
            return newInstance_(QuickAndroidQrCodeFragment::class.java, intentParam)
        }
    }

    override fun getRes(): Int = R.layout.fragment_quick_android_qrcode

    override fun initView() {
        zxingview.setDelegate(object : QRCodeView.Delegate {
            override fun onScanQRCodeSuccess(result: String?) {
                // 识别成功，震动
                activity?.vibrator()

                Intent()
                    .putExtra(QR_CODE_RESULT, result).let {
                        activity?.setResult(0, it)
                        activity?.finish()
                    }

            }

            override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
                "onCameraAmbientBrightnessChanged => isDark: $isDark".i()
            }

            override fun onScanQRCodeOpenCameraError() {
                Intent()
                    .putExtra(QR_CODE_RESULT, "打开相机失败").let {
                        activity?.setResult(0, it)
                        activity?.finish()
                    }
                "打开相机失败".e()
            }

        })


        requestCamera()
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsGranted(requestCode, perms)

        // 打开后摄像头开始预览，但是并没有开始识别
        zxingview.startCamera()

        // 显示扫描框，并开始识别
        zxingview.startSpotAndShowRect()
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsDenied(requestCode, perms)
        Intent()
            .putExtra(QR_CODE_RESULT, "获取相机权限失败").let {
                activity?.setResult(0, it)
                activity?.finish()
            }
    }

    override fun onStop() {
        super.onStop()
        zxingview.stopCamera()
    }

    override fun onDestroy() {
        zxingview?.onDestroy()
        super.onDestroy()
    }


    override fun initialLoadData() {
    }


}