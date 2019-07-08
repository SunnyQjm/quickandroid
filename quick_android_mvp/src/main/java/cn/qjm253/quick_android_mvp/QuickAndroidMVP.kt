package cn.qjm253.quick_android_mvp

import android.app.Application
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_mvp.model.inernet.QuickAndroidMVPAPIManager
import cn.qjm253.quick_android_mvp.model.inernet.rx.QuickAndroidMVPResultDeal
import com.github.anzewei.parallaxbacklayout.ParallaxHelper

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
object QuickAndroidMVP {

    //////////////////////////////////////////
    /////// 初始化操作
    //////////////////////////////////////////

    /**
     * QuickAndroidMVP 模块初始化操作
     *
     * @param context                       Application 对象
     * @param baseUrl                       网络请求的前缀， eg.: https://example.cn/
     * @param enablePersistentCookieJar     是否开启Cookie的自动持久化和发送
     */
    fun init(
        context: Application,
        baseUrl: String = "",
        enablePersistentCookieJar: Boolean = true
    ): QuickAndroidMVP {
        QuickAndroidMVPAPIManager.init(context, enablePersistentCookieJar, baseUrl)
        return this
    }





    ////////////////////////////////////////////
    //////// 网络请求统一处理
    ////////////////////////////////////////////
    val onCompleteListeners = mutableListOf<() -> Unit>()


    /**
     * 添加一个网络请求 onComplete 的拦截监听
     * 每一个使用 Observable<T>.qaSubscribe 进行订阅的网络请求的onComplete执行的时候都会触发
     *
     * @see cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
     */
    fun addOnAPICompleteListener(onComplete: () -> Unit): QuickAndroidMVP {
        onCompleteListeners.add(onComplete)
        return this
    }



    fun clearOnAPICompleteListeners(): QuickAndroidMVP {
        onCompleteListeners.clear()
        return this
    }


    val onBeforeNextListeners = mutableListOf<() -> Unit>()

    /**
     * 添加一个网络请求 onNext 的拦截监听
     * 每一个使用 Observable<T>.qaSubscribe 进行订阅的网络请求的onNext执行的之前都会触发
     *
     * @see cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
     */
    fun addOnAPIBeforeNextListener(onBeforeNextListener: () -> Unit): QuickAndroidMVP {
        onBeforeNextListeners.add(onBeforeNextListener)
        return this
    }

    fun clearOnAPIBeforeNextListeners(): QuickAndroidMVP {
        onBeforeNextListeners.clear()
        return this
    }

    val onErrorListeners = mutableListOf<(Throwable) -> Unit>()

    /**
     * 添加一个网络请求 onError 的拦截监听
     * 每一个使用 Observable<T>.qaSubscribe 进行订阅的网络请求的onError执行的时候都会触发
     *
     * @see cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
     */
    fun addOnAPIErrorListener(onErrorListener: (Throwable) -> Unit): QuickAndroidMVP {
        onErrorListeners.add(onErrorListener)
        return this
    }

    fun clearOnAPIErrorListeners() {
        onErrorListeners.clear()
    }


    /////////////////////////////////////////////////////
    ///////////  网络请求结果统一处理
    /////////////////////////////////////////////////////


    var quickAndroidMVPResultDeal: QuickAndroidMVPResultDeal? = null

    /**
     * 注册App网络请求结果的统一处理
     *
     * @see QuickAndroidMVPResultDeal
     */
    fun registerAPIResultDeal(quickAndroidMVPResultDeal: QuickAndroidMVPResultDeal): QuickAndroidMVP {
        this.quickAndroidMVPResultDeal = quickAndroidMVPResultDeal
        return this
    }

}



/*******************************************************
 * 给 QuickAndroid 添加的扩展
 *
 * @see cn.qjm253.quick_android_base.QuickAndroid
 *
 ******************************************************/


/**
 * @see QuickAndroidMVP.init
 */
fun QuickAndroid.initMVP(
    context: Application,
    baseUrl: String = "",
    enablePersistentCookieJar: Boolean = true
): QuickAndroid {
    QuickAndroidMVP.init(context, baseUrl, enablePersistentCookieJar)
    return this
}

/**
 * @see QuickAndroidMVP.addOnAPICompleteListener
 */
fun QuickAndroid.addOnAPICompleteListener(onComplete: () -> Unit): QuickAndroid {
    QuickAndroidMVP.addOnAPICompleteListener(onComplete)
    return this
}


/**
 * @see QuickAndroidMVP.clearOnAPICompleteListeners
 */
fun QuickAndroid.clearOnAPICompleteListeners(): QuickAndroid {
    QuickAndroidMVP.clearOnAPICompleteListeners()
    return this
}

/**
 * @see QuickAndroidMVP.addOnAPIBeforeNextListener
 */
fun QuickAndroid.addOnAPIBeforeNextListener(onBeforeNextListener: () -> Unit): QuickAndroid {
    QuickAndroidMVP.addOnAPIBeforeNextListener(onBeforeNextListener)
    return this
}

/**
 * @see QuickAndroidMVP.clearOnAPIBeforeNextListeners
 */
fun QuickAndroid.clearOnAPIBeforeNextListeners(): QuickAndroid {
    QuickAndroidMVP.clearOnAPIBeforeNextListeners()
    return this
}

/**
 * @see QuickAndroidMVP.addOnAPIErrorListener
 */
fun QuickAndroid.addOnAPIErrorListener(onErrorListener: (Throwable) -> Unit): QuickAndroid {
    QuickAndroidMVP.addOnAPIErrorListener(onErrorListener)
    return this
}

/**
 * @see QuickAndroidMVP.clearOnAPIErrorListeners
 */
fun QuickAndroid.clearOnAPIErrorListeners(): QuickAndroid {
    QuickAndroidMVP.clearOnAPIErrorListeners()
    return this
}

/**
 * @see QuickAndroidMVP.registerAPIResultDeal
 */
fun QuickAndroid.registerAPIResultDeal(quickAndroidMVPResultDeal: QuickAndroidMVPResultDeal): QuickAndroid {
    QuickAndroidMVP.registerAPIResultDeal(quickAndroidMVPResultDeal)
    return this
}
