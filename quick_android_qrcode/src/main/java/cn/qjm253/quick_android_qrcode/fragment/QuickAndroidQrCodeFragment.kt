package cn.qjm253.quick_android_qrcode.fragment

import android.content.Intent
import android.widget.RelativeLayout
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.extensions.vibrator
import cn.qjm253.quick_android_base.params.IntentParam
import cn.qjm253.quick_android_base.util.DisplayUtils
import cn.qjm253.quick_android_qrcode.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.fragment_quick_android_qrcode.*


/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

class QuickAndroidQrCodeFragment : BaseQuickAndroidFragment() {

    companion object {
        const val QR_CODE_RESULT = "QR_CODE_RESULT"
        const val QR_CODE_ERROR = "QR_CODE_ERROR"
        const val QR_CODE_BACK_ICON_RES = "QR_CODE_BACK_ICON_RES"
        const val QR_CODE_BACK_ICON_SIZE = "QR_CODE_BACK_ICON_SIZE"
        const val QR_CODE_BAKC_ICON_MARGIN = "QR_CODE_BAKC_ICON_MARGIN"

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
//                "onCameraAmbientBrightnessChanged => isDark: $isDark".i()
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

        val statusBarHeight = ImmersionBar.getStatusBarHeight(this);

        activity?.intent?.let {
            val backIconRes =
                it.getIntExtra(QR_CODE_BACK_ICON_RES, R.drawable.quick_android_qr_code_default_back)
            val backIconSize = it.getFloatExtra(QR_CODE_BACK_ICON_SIZE, 30f)
            val backIconMargin: FloatArray =
                it.getFloatArrayExtra(QR_CODE_BAKC_ICON_MARGIN) ?: floatArrayOf(15f, 0f, 0f, 15f)
            RelativeLayout.LayoutParams(imgBack.layoutParams).let { lp ->
                lp.setMargins(
                    DisplayUtils.dip2px(context, backIconMargin[3]),
                    DisplayUtils.dip2px(context, backIconMargin[0]) + statusBarHeight,
                    DisplayUtils.dip2px(context, backIconMargin[1]),
                    DisplayUtils.dip2px(context, backIconMargin[2])
                )
                lp.width = DisplayUtils.dip2px(context, backIconSize)
                lp.height = DisplayUtils.dip2px(context, backIconSize)
                imgBack.layoutParams = lp
            }
            imgBack.setImageResource(backIconRes)
        }

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

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