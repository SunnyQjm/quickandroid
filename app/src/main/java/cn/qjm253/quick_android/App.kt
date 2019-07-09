package cn.qjm253.quick_android

import android.app.Application
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_mvp.*
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_mvp.model.inernet.rx.QuickAndroidMVPResultDeal
import com.orhanobut.logger.Logger
import com.tencent.smtt.sdk.QbSdk
import io.reactivex.Observable

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        QuickAndroid.appName("quick_android_demo")
            .init()
            .enableParallaxBack(this)
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
            .registerAPIResultDeal(object : QuickAndroidMVPResultDeal(
                arrayOf(
                    -1 to "网络请求失败"
                ),
                arrayOf(
                    0 to "网络请求成功"
                ),
                arrayOf(
                    -2 to "Token失效"
                )
            ) {
                override fun dealRetry(t: Throwable): Observable<Int> {
                    t.e("dealRetry")
                    return Observable.error(t)
                }

                override fun normalDeal(code: Int, description: String) {
                    "normalDeal => code: $code, description: $description".i()
                }

                override fun errorDeal(code: Int, description: String) {
                    "errorDeal => code: $code, description: $description".e()
                }

            })
        QbSdk.initX5Environment(this, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(p0: Boolean) {

            }

            override fun onCoreInitFinished() {
            }

        })
    }
}