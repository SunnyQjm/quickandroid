package cn.qjm253.quick_android

import android.Manifest
import android.os.Build
import android.os.Bundle
import cn.qjm253.quick_android.mvp_demo.MVPDemoActivity
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.extensions.jumpTo
import cn.qjm253.quick_android_base.extensions.toJson
import cn.qjm253.quick_android_image_picker.openWechatStyleGallery
import cn.qjm253.quick_android_qrcode.activity.QuickAndroidQrCodeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseQuickAndroidActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpDemoBtn.setOnClickListener {
            jumpTo(MVPDemoActivity::class.java)
        }

        btnScanQrCode.setOnClickListener {
            jumpTo(QuickAndroidQrCodeActivity::class.java)
        }

        btnWechatStyleImagePicker.setOnClickListener {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 1)
            } else {
                onPermissionsGranted(1, permissions.toMutableList())
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsGranted(requestCode, perms)
        when(requestCode) {
            1 -> {          // 打开微信样式的图片选择器
                openWechatStyleGallery()
                    .subscribe({
                        it.toJson().i()
                    }, {
                        it.e()
                    }, {
                        "wechat style imagePicker complete".i()
                    })
            }
        }
    }
}
