package cn.qjm253.quick_android_webview.views

/**
 * @author SunnyQjm
 * @date 19-7-7 下午11:24
 */

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import cn.qjm253.quick_android_image_picker.MyImagePicker
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.*
import org.jetbrains.anko.selector

class XbWebView(context: Context, attributeSet: AttributeSet) :
    WebView(context, attributeSet) {

    var onWebViewLoadListener: OnWebViewLoadListener? = null

    init {
        this.webViewClient = object : WebViewClient() {

            /**
             * 防止加载网页时调起系统浏览器
             */
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                p0?.loadUrl(p1 ?: "")
                return true
            }

            override fun onReceivedError(p0: WebView?, p1: WebResourceRequest?, p2: WebResourceError?) {
                super.onReceivedError(p0, p1, p2)
                p2?.let {
                    onWebViewLoadListener?.onLoadFail(p2)
                }
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                onWebViewLoadListener?.onLoadSuccess()
            }
        }
        this.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                onWebViewLoadListener?.onProgress(p1)
            }

            override fun openFileChooser(p0: ValueCallback<Uri>?, p1: String?, p2: String?) {
//                rxPermissions
//                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    .subscribe { granted ->
//                        if (granted) {
//                            openImageChooserActivity(context, p0)
//                        }
//                    }
            }

            override fun onShowFileChooser(
                p0: WebView?,
                p1: ValueCallback<Array<Uri>>?,
                p2: FileChooserParams?
            ): Boolean {
//                rxPermissions
//                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    .subscribe { granted ->
//                        if (granted) {
//                            openImageChooserActivity(context, null, p1)
//                        }
//                    }
                return true
            }
        }
        initWebViewSettings()
    }

    /**
     * 打开图片选择
     */
    private fun openImageChooserActivity(
        context: Context, valueCallback: ValueCallback<Uri>? = null,
        valueCallback2: ValueCallback<Array<Uri>>? = null
    ) {
        context.selector("选择获取图片方式", listOf("拍照", "相册")) { _, i ->
            when (i) {
                0 -> {          // 拍照
                    // 使用摄像头拍摄
                    RxImagePicker.create()
                        .openCamera(context)
                        .subscribe { result ->
                            valueCallback?.onReceiveValue(result.uri)
                            valueCallback2?.onReceiveValue(arrayOf(result.uri))
                        }
                }
                1 -> {          // 相册选择
                    val result = mutableListOf<Uri>()
                    // 打开相册选择
                    RxImagePicker.create(MyImagePicker::class.java)
                        .openZhihuStyleGallery(
                            context,
                            WechatConfigrationBuilder(MimeType.INSTANCE.ofImage(), false)
                                .maxSelectable(9)
                                .countable(true)
                                .spanCount(4)
                                .countable(false)
                                .build()
                        )
                        .subscribe({ res ->
                            // onNext
                            result.add(res.uri)
                        }, {
                            // onError

                        }, {
                            // onComplete
                            if(result.size <= 0) {
                                valueCallback2?.onReceiveValue(null)
                            } else {
                                valueCallback2?.onReceiveValue(result.toTypedArray())
                            }
                        })

                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        val webSetting = this.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.cacheMode = WebSettings.LOAD_NORMAL

        webSetting.setAllowUniversalAccessFromFileURLs(true)

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }


    interface OnWebViewLoadListener {
        fun onLoadSuccess()
        fun onLoadFail(error: WebResourceError)
        fun onProgress(progress: Int)
    }
}