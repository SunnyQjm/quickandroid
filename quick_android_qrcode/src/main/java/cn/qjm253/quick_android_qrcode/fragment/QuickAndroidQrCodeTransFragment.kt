package cn.qjm253.quick_android_qrcode.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.extensions.jumpForResult
import cn.qjm253.quick_android_base.extensions.toJson
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

    var emitter: ObservableEmitter<QrCodeResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    fun <T : BaseQuickAndroidActivity> scanCode(clazz: Class<T>): Observable<QrCodeResult> {
        return Observable.create { emitter ->
            // 保存事件发送器
            this.emitter = emitter
            jumpForResult(clazz, 0)
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