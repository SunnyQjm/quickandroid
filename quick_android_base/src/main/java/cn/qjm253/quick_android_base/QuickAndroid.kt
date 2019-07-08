package cn.qjm253.quick_android_base

import android.app.Application
import android.content.Context
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
object QuickAndroid {
    var APP_NAME = "QuickAndroid"

    fun appName(appName: String = "quick_android"): QuickAndroid {
        APP_NAME = appName
        return this
    }

    fun init(): QuickAndroid {
        Logger.addLogAdapter(AndroidLogAdapter())
        return this
    }

    /**
     * 开启侧滑
     * 如需修改侧滑触发的模式和位置，参看BaseQuickAndroidActivity
     * @see cn.qjm253.quick_android_mvp.base.activity.BaseQuickAndroidActivity
     */
    fun enableParallaxBack(context: Application) {
        /**
         * 初始化侧滑操作
         * 需要在Application当中初始化
         */
        context.registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())
    }
}