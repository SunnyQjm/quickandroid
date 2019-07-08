package cn.qjm253.quick_android

import android.Manifest
import android.os.Bundle
import cn.qjm253.quick_android.mvp_demo.MVPDemoActivity
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.extensions.jumpTo
import cn.qjm253.quick_android_base.extensions.toJson
import cn.qjm253.quick_android_base.util.ContentUriUtil
import cn.qjm253.quick_android_base.util.RxSchedulersHelper
import cn.qjm253.quick_android_image_picker.openWechatStyleGallery
import cn.qjm253.quick_android_image_picker.startClipImage
import cn.qjm253.quick_android_qrcode.scanCode
import cn.qjm253.quick_android_rx_permission.QuickAndroidRxPermission
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseQuickAndroidActivity() {

    val rxPermission = QuickAndroidRxPermission(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpDemoBtn.setOnClickListener {
            jumpTo(MVPDemoActivity::class.java)
        }

        btnScanQrCode.setOnClickListener {
            rxPermission
                .request(arrayOf(Manifest.permission.CAMERA), "摄像头")
                .compose(RxSchedulersHelper.io_main())
                .subscribe( {
                    "fuck?".i()
                    if(it.granted) {
                        scanCode()
                    }
                }, {
                    it.e()
                }, {
                    "complete".i()
                })
//            easyRequestPermissions(arrayOf(Manifest.permission.CAMERA), R.string.camera_permission_require, 2)
        }

        btnWechatStyleImagePicker.setOnClickListener {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            rxPermission
                .request(permissions, "啦啦啦")
                .subscribe {
                    if(it.granted) {
                        openWechatStyleGallery(
                            WechatConfigrationBuilder(
                                MimeType.ofImage(),
                                false
                            )
                                .maxSelectable(1)
                                .countable(true)
                                .spanCount(4)
                                .build()
                        )
                            .subscribe({
                                startClipImage(ContentUriUtil.getPath(this, it.uri))
                                it.toJson().i()
                            }, {
                                it.e()
                            }, {
                                "wechat style imagePicker complete".i()
                            })
                    } else {

                    }
                }
//            easyRequestPermissions(permissions, R.string.image_picker_require_rw_permission, 1)
        }
    }

//    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        super.onPermissionsGranted(requestCode, perms)
//        when (requestCode) {
//            1 -> {          // 打开微信样式的图片选择器
//                openWechatStyleGallery(
//                    WechatConfigrationBuilder(
//                        MimeType.ofImage(),
//                        false
//                    )
//                        .maxSelectable(1)
//                        .countable(true)
//                        .spanCount(4)
//                        .build()
//                )
//                    .subscribe({
//                        startClipImage(ContentUriUtil.getPath(this, it.uri))
//                        it.toJson().i()
//                    }, {
//                        it.e()
//                    }, {
//                        "wechat style imagePicker complete".i()
//                    })
//            }
//            2 -> {          // 执行扫码操作
//                scanCode()
//            }
//        }
//    }
}
