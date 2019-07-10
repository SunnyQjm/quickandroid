package cn.qjm253.quick_android_webview

import androidx.fragment.app.FragmentActivity
import cn.qjm253.quick_android_webview.views.QuickAndroidWebview
import cn.qjm253.quick_android_webview.web_bridge.QuickAndroidWebBridge

/**
 * @author SunnyQjm
 * @date 19-7-10 下午5:29
 */
object QuickAndroidWV{

    fun init(context: FragmentActivity, quickAndroidWebview: QuickAndroidWebview): QuickAndroidWV {
        QuickAndroidWebBridge(context)
            .bindWebView(quickAndroidWebview)
        return this
    }
}