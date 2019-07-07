package cn.qjm253.quick_android_base.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:52
 */

/**
 * Activity触发震动的扩展
 * @param duration  震动持续时间
 * @param amplitude 震动的强度，限制在 1~255 之间
 */
fun Activity.vibrator(duration: Long = 200, amplitude: Int = 100) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
    } else {
        vibrator.vibrate(duration)
    }
}


fun <T: Fragment> FragmentActivity.addFragmentToActivity(@IdRes fragmentLayoutId: Int, fragmentGenerator: () -> T): T {
    var fragment = this.supportFragmentManager.findFragmentById(fragmentLayoutId) as? T
    if(fragment == null) {
        fragment = fragmentGenerator()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fragmentLayoutId, fragment)
        transaction.commit()
    }
    return fragment
}