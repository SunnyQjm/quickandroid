package cn.qjm253.quick_android_mvp

import android.app.Application
import cn.qjm253.quick_android_mvp.model.inernet.QuickAndroidMVPAPIManager
import com.github.anzewei.parallaxbacklayout.ParallaxHelper

object QuickAndroidMVP {

    fun init(
        context: Application,
        enablePersistentCookieJar: Boolean = true,
        baseUrl: String = ""
    ) {
        initParallaxBackLayout(context)
        QuickAndroidMVPAPIManager.init(context, enablePersistentCookieJar, baseUrl)
    }

    /**
     * 初始化侧滑操作
     * 需要在Application当中初始化
     */
    fun initParallaxBackLayout(context: Application): QuickAndroidMVP {
        context.registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())
        return this
    }
}