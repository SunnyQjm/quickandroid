package cn.qjm253.quick_android_base.util.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.R
import cn.qjm253.quick_android_base.params.IntentParam

/**
 * 通知显示工具类
 * Created by sunny on 18-1-30.
 */

class MyNotificationUtil private constructor(val context: Context, channelId: String = QuickAndroid.APP_NAME) {

    private var notification: Notification? = null
    private var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var builder = NotificationCompat.Builder(context, channelId)

    companion object {
        private var default_notifyId = 0
        fun newInstance(context: Context): MyNotificationUtil {
            return MyNotificationUtil(context)
        }

        @Synchronized
        fun generateNotifyId(): Int {
            default_notifyId = (default_notifyId + 1) % 100
            return default_notifyId
        }

        fun createChannelForOreo(context: Context, channelId: String, channelName: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                channel.enableLights(true)
                channel.setShowBadge(true)
                channel.lightColor = Color.GREEN
                channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun notify(notification: Notification, notificationId: Int){
        notificationManager.notify(notificationId, notification)
    }

    fun cancel(notificationId: Int){
        notificationManager.cancel(notificationId)
    }


    fun initProgress(progress: Int, progressMax: Int = 100, indeterminate: Boolean = true,
                     title: String = "", content: String = "", ticker: String = ""): MyNotificationUtil {
        initNotification(progress = progress, progressMax = progressMax, indeterminate = indeterminate,
            title = title, content = content, ticker = ticker, isProgress = true, isSound = false,
            isVibrate = false, autoCancel = false)
        return this
    }


    fun initNotification(@DrawableRes smallIcon: Int = R.drawable.logo,
                         @DrawableRes bigIcon: Int = R.drawable.logo, title: String = "",
                         content: String = "", ticker: String = "", time: Long = System.currentTimeMillis(),
                         /**
                          * 1.将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
                          * 不设置的话点击消息后也不清除，但可以滑动删除
                          * 2.将Ongoing设为true 那么notification将不能滑动删除
                          */
                         autoCancel: Boolean = true, onGoing: Boolean = false,
                         /**
                          * 从Android4.1开始，可以通过以下方法，设置notification的优先级，
                          * 优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
                          */
                         priority: Int = NotificationCompat.PRIORITY_DEFAULT,
                         /**
                          * Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
                          * Notification.DEFAULT_SOUND：系统默认铃声。
                          * Notification.DEFAULT_VIBRATE：系统默认震动。
                          * Notification.DEFAULT_LIGHTS：系统默认闪光。
                          * notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
                          */
                         isSound: Boolean = true, isVibrate: Boolean = true, isLight: Boolean = true,
                         /**
                          * 下面是进度相关设置
                          */
                         progress: Int = 0, progressMax: Int = 100, indeterminate: Boolean = true,
                         /**
                          * 是否显示进度条
                          */
                         isProgress: Boolean = false,
                         /**
                          * 点击跳转相关设置
                          * 注意事项： setContentIntent一定要在build之前调用，不然无效
                          */
                         requestCode: Int = 0, target: Class<*>? = null,
                         pendingIntentFlag: Int = PendingIntent.FLAG_UPDATE_CURRENT,
                         pendingIntentParam: IntentParam? = null,

                         // 适配Android8.0+ 通知必须配置渠道，要不然无法在通知栏显示
                         channelId: String = QuickAndroid.APP_NAME,
                         channelName: String = QuickAndroid.APP_NAME
                         )
            : MyNotificationUtil {
        // 适配Android8.0+ 通知必须配置渠道，要不然无法在通知栏显示
        createChannelForOreo(context, channelId, channelName)

        builder.setAutoCancel(autoCancel)
        builder.setSmallIcon(smallIcon)
        val bitmap = BitmapFactory.decodeResource(context.resources, bigIcon)
        builder.setLargeIcon(bitmap)
        builder.setContentText(content)
        builder.setContentTitle(title)
        builder.setTicker(ticker)
        builder.setWhen(time)
        builder.priority = priority
        builder.setOngoing(onGoing)
        var defaults = 0
        if (isSound) {
            defaults = defaults or Notification.DEFAULT_SOUND
        }
        if (isVibrate) {
            defaults = defaults or Notification.DEFAULT_VIBRATE
        }
        if (isLight) {
            defaults = defaults or Notification.DEFAULT_LIGHTS
        }

        if (isProgress)
            builder.setProgress(progressMax, progress, indeterminate)
        if (target != null) {
            val intent = Intent(context, target)
            pendingIntentParam?.applyParam(intent)
            builder.setContentIntent(PendingIntent.getActivity(context, requestCode,
                intent, pendingIntentFlag))
        }
        builder.setDefaults(defaults)
        return this
    }

    fun show(notificationId: Int = generateNotifyId()): Int {
        notification = builder.build()
        notificationManager.notify(notificationId, notification)
        return notificationId
    }
}
