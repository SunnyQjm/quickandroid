package cn.qjm253.quick_android

import android.app.Application
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_mvp.*
import com.orhanobut.logger.Logger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        QuickAndroid.appName("quick_android_demo")
            .init(this)
            .initMVP(this, baseUrl = "https://www.tianqiapi.com/")
            .addOnAPIBeforeNextListener {
                Logger.i("before API next")
            }
            .addOnAPIBeforeNextListener {
                Logger.i("before API next")
            }
            .addOnAPIErrorListener {
                Logger.e("API onError")
            }
            .addOnAPICompleteListener {
                Logger.i("API onComplete")
            }
    }
}