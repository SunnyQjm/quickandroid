package cn.qjm253.quick_android_qrcode.fragment

import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.extensions.jumpForResult
import cn.qjm253.quick_android_base.extensions.toJson
import cn.qjm253.quick_android_base.params.IntentParam
import cn.qjm253.quick_android_qrcode.R
import cn.qjm253.quick_android_qrcode.model.QrCodeResult
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * 这个Fragment用于跳转到扫码界面，并接收其返回的结果，然后使用RxJava进行封装，便于链式调用
 *
 * @author SunnyQjm
 * @date 19-7-8 下午3:22
 */
class QuickAndroidQrCodeTransFragment : Fragment() {

    private var emitter: ObservableEmitter<QrCodeResult>? = null

    private var backIconRes: Int = R.drawable.quick_android_qr_code_default_back
    private var backIconSize: Float = 30f
    private var backIconTopMargin: Float = 15f
    private var backIconRightMargin: Float = 0f
    private var backIconBottomMargin: Float = 0f
    private var backIconLeftMargin: Float = 15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /**
     * 设置返回按钮的资源
     */
    fun setBackIconRes(@DrawableRes icon: Int): QuickAndroidQrCodeTransFragment {
        backIconRes = icon
        return this
    }

    /**
     * 设置返回按钮的大小
     * ps: 单位为dp
     */
    fun setBackIconSize(size: Float): QuickAndroidQrCodeTransFragment {
        backIconSize = size
        return this
    }

    /**
     * 设置返回按钮的边距
     * 1. 单位为dp
     * 2. 代码中已经自动去掉了状态栏的高度，不用考虑状态栏
     */
    fun setBackIconMargin(
        top: Float,
        right: Float,
        bottom: Float,
        left: Float
    ): QuickAndroidQrCodeTransFragment {
        backIconTopMargin = top
        backIconRightMargin = right
        backIconBottomMargin = bottom
        backIconLeftMargin = left
        return this
    }

    fun <T : BaseQuickAndroidActivity> scanCode(clazz: Class<T>): Observable<QrCodeResult> {
        return Observable.create { emitter ->
            // 保存事件发送器
            this.emitter = emitter
            jumpForResult(
                clazz, 0, IntentParam()
                    .add(QuickAndroidQrCodeFragment.QR_CODE_BACK_ICON_RES, backIconRes)
                    .add(QuickAndroidQrCodeFragment.QR_CODE_BACK_ICON_SIZE, backIconSize)
                    .add(
                        QuickAndroidQrCodeFragment.QR_CODE_BAKC_ICON_MARGIN, floatArrayOf(
                            backIconTopMargin, backIconRightMargin,
                            backIconBottomMargin, backIconLeftMargin
                        )
                    )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {      // 扫码结果处理
                data?.let {
                    val qrCodeResult = it.getStringExtra(QuickAndroidQrCodeFragment.QR_CODE_RESULT)
                    val qrCodeError = it.getStringExtra(QuickAndroidQrCodeFragment.QR_CODE_ERROR)
                    qrCodeResult?.i()
                    qrCodeError?.e()
                    when {
                        qrCodeResult != null -> this.emitter?.onNext(QrCodeResult(qrCodeResult))
                        qrCodeError != null -> this.emitter?.onError(Throwable(qrCodeError))
                        else -> {

                        }
                    }
                    this.emitter?.onComplete()
                }
            }
        }
    }
}