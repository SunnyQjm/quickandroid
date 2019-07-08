package cn.qjm253.quick_android_webview.web_bridge

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import androidx.fragment.app.FragmentActivity
import cn.qjm253.quick_android_base.extensions.toast
import cn.qjm253.quick_android_qrcode.QuickAndroidQrCode
import cn.qjm253.quick_android_qrcode.scanCode
import cn.qjm253.quick_android_webview.views.QuickAndroidWebview
import com.tencent.smtt.sdk.ValueCallback

/**
 * 与WebView中的Js相互通信的工具类
 *
 * @author SunnyQjm
 * @date 19-7-8 下午2:48
 */

class QuickAndroidWebBridge(
    private val context: FragmentActivity
) {
    companion object {
        const val JS_CALLER_NAME = "QuickAndroidJsCallEr"


        //////////////////////////////////////////////
        /////// 回调名称定义
        //////////////////////////////////////////////
        const val CALLBACK_BASE_NAME = "Quick_Android_Js_Callback"

        /**
         * 扫码相关回调
         */
        const val CALLBACK_SCAN_CODE_SUCCESS = "${CALLBACK_BASE_NAME}scan_code_success"     // 扫码成功回调名
        const val CALLBACK_SCAN_CODE_FAIL = "${CALLBACK_BASE_NAME}scan_code_fail"           // 扫码失败回调名
    }

    private var webView: QuickAndroidWebview? = null

    /**
     * 绑定到WebView上
     */
    fun bindWebView(webview: QuickAndroidWebview) {
        webview.addJavascriptInterface(context, JS_CALLER_NAME)
        this.webView = webView
    }

    /**
     * 提供给Js调用的显示toast接口
     */
    @JavascriptInterface
    fun showToast(msg: String, duration: Int = 500) {
        context.toast(msg, duration)
    }


    /**
     * 提供给Js调用的扫码接口
     */
    @SuppressLint("CheckResult")
    @JavascriptInterface
    fun scanCode() {
        // 调用原生的扫码接口
        QuickAndroidQrCode.create(context)
        context.scanCode()
            .subscribe({
                // 扫码结果
                callJs(CALLBACK_SCAN_CODE_SUCCESS, it.content)
            }, {
                // 扫码出错
                callJs(CALLBACK_SCAN_CODE_FAIL, it.message ?: "扫码出错")
            })
    }


    /**
     * 调用Js的回调函数，并且支持传递一个字符串参数
     */
    fun callJs(callbackName: String, param: String = "", callback: ValueCallback<String> = ValueCallback { }) {
        webView?.post {
            // 执行JS的代码，需要在主线程中进行
            webView?.evaluateJavascript("javascript:$callbackName('$param')", callback)
        }
    }

}