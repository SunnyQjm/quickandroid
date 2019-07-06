package cn.qjm253.quick_android

import android.app.Application
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_mvp.QuickAndroidMVP

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        QuickAndroid.appName("quick_android_demo")
        QuickAndroidMVP.init(this, baseUrl = "https://www.tianqiapi.com/")
    }
}