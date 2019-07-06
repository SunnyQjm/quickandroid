package cn.qjm253.quick_android_mvp

import android.app.Application
import cn.qjm253.quick_android_mvp.model.inernet.QuickAndroidMVPAPIManager
import com.github.anzewei.parallaxbacklayout.ParallaxHelper

object QuickAndroidMVP {

    //////////////////////////////////////////
    /////// 初始化操作
    //////////////////////////////////////////

    fun init(
        context: Application,
        enablePersistentCookieJar: Boolean = true,
        baseUrl: String = ""
    ): QuickAndroidMVP {
        initParallaxBackLayout(context)
        QuickAndroidMVPAPIManager.init(context, enablePersistentCookieJar, baseUrl)
        return this
    }

    /**
     * 初始化侧滑操作
     * 需要在Application当中初始化
     */
    fun initParallaxBackLayout(context: Application): QuickAndroidMVP {
        context.registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())
        return this
    }



    ////////////////////////////////////////////
    //////// 网络请求统一处理
    ////////////////////////////////////////////
    val onCompleteListeners = mutableListOf<() -> Unit>()

    fun addOnAPICompleteListener(onComplete: () -> Unit): QuickAndroidMVP {
        onCompleteListeners.add(onComplete)
        return this
    }

    fun clearOnAPICompleteListeners(): QuickAndroidMVP {
        onCompleteListeners.clear()
        return this
    }


    val onBeforeNextListeners = mutableListOf<() -> Unit>()

    fun addOnAPIBeforeNextListener(onBeforeNextListener: () -> Unit): QuickAndroidMVP {
        onBeforeNextListeners.add(onBeforeNextListener)
        return this
    }

    fun clearOnAPIBeforeNextListeners(): QuickAndroidMVP {
        onBeforeNextListeners.clear()
        return this
    }

    val onErrorListeners = mutableListOf<(Throwable) -> Unit>()

    fun addOnAPIErrorListener(onErrorListener: (Throwable) -> Unit): QuickAndroidMVP {
        onErrorListeners.add(onErrorListener)
        return this
    }

    fun clearOnAPIErrorListeners() {
        onErrorListeners.clear()
    }

}