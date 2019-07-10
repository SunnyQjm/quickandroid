package cn.qjm253.quick_android.webview_demo

import android.os.Bundle
import cn.qjm253.quick_android.R
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_webview.QuickAndroidWV
import cn.qjm253.quick_android_webview.views.QuickAndroidWebview
import cn.qjm253.quick_android_webview.web_bridge.QuickAndroidWebBridge
import kotlinx.android.synthetic.main.activity_web_view_demo.*

class WebViewDemo : BaseQuickAndroidActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_demo)

        QuickAndroidWV
            .init(this, quickAndroidWebview)

        quickAndroidWebview.loadUrl("file:///android_asset/www/test.html")
    }
}
