package cn.qjm253.quick_android_base

import android.app.Application
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

    fun init(context: Application): QuickAndroid {
        Logger.addLogAdapter(AndroidLogAdapter())
        return this
    }
}