package cn.qjm253.quick_android

import android.Manifest
import android.os.Bundle
import cn.qjm253.quick_android.custom_view_demo.CustomViewDemoActivity
import cn.qjm253.quick_android.mvp_demo.MVPDemoActivity
import cn.qjm253.quick_android.webview_demo.WebViewDemo
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_base.extensions.*
import cn.qjm253.quick_android_base.util.ContentUriUtil
import cn.qjm253.quick_android_base.util.RxSchedulersHelper
import cn.qjm253.quick_android_easy_bar.EasyBar
import cn.qjm253.quick_android_easy_bar.init
import cn.qjm253.quick_android_image_picker.openWechatStyleGallery
import cn.qjm253.quick_android_image_picker.startClipImage
import cn.qjm253.quick_android_qrcode.QuickAndroidQrCode
import cn.qjm253.quick_android_rx_permission.QuickAndroidRxPermission
import cn.qjm253.quick_android_rx_permission.createRxPermission
import com.example.quick_android_file_picker.QuickAndroidFilePicker
import com.example.quick_android_file_picker.createFilePicker
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseQuickAndroidActivity() {

    private val rxPermission = QuickAndroidRxPermission(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        easyBar.init(
            mode = EasyBar.Mode.NONE,
            title = "QuickAndroid"
        )


        mvpDemoBtn.setOnClickListener {
            jumpTo(MVPDemoActivity::class.java)
        }

        btnScanQrCode.setOnClickListener {
            rxPermission
                .request(arrayOf(Manifest.permission.CAMERA), "摄像头")
                .compose(RxSchedulersHelper.io_main())
                .subscribe({
                    "fuck?".i()
                    if (it.granted) {
                        QuickAndroidQrCode.create(this)
                            .setBackIconRes(R.drawable.back)
                            .setBackIconSize(30f)
                            .setBackIconMargin(15f, 0f, 0f, 15f)
                            .scanQrCode()
                            .subscribe { qrResult ->
                                toast(qrResult.content)
                            }
                    }
                }, {
                    it.e()
                }, {
                    "complete".i()
                })
        }

        btnWechatStyleImagePicker.setOnClickListener {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            rxPermission
                .request(permissions, "啦啦啦")
                .subscribe {
                    if (it.granted) {
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

        btnWebviewDemo.setOnClickListener {
            jumpTo(WebViewDemo::class.java)
        }

        btnCustomViewDemo.setOnClickListener {
            jumpTo(CustomViewDemoActivity::class.java)
        }

        btnPickerFile.setOnClickListener {
            QuickAndroid
                .createRxPermission(this)
                .request(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
                .subscribe {
                    if (it.granted) {
                        checkMyPermission(Manifest.permission.READ_EXTERNAL_STORAGE, {
                            QuickAndroid
                                .createFilePicker(this)
                                .pickerFile()
                                .subscribe { fpr ->
                                    toast(fpr.path)
                                }
                        })
                    }
                }
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
