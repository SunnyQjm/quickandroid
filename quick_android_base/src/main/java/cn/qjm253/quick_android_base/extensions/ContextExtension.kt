package cn.qjm253.quick_android_base.extensions

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import cn.qjm253.quick_android_base.params.IntentParam
import java.util.*

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

fun Context.toast(info: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, info, duration)
        .show()
}

fun Context.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringRes, duration)
        .show()
}


fun Context.jumpTo(cls: Class<*>, vararg flags: Int) {
    val intent = Intent(this, cls)
    flags.forEach {
        intent.addFlags(it)
    }
    startActivity(intent)
}


fun Context.jumpTo(cls: Class<*>, intentParam: IntentParam? = null, vararg flags: Int) {
    val intent = Intent(this, cls)
    flags.forEach {
        intent.addFlags(it)
    }
    intentParam?.applyParam(intent)
    startActivity(intent)
}

fun Activity.jumpForResult(cls: Class<*>, requestCode: Int = 0, intentParam: IntentParam? = null) {
    val intent = Intent(this, cls)
    intentParam?.applyParam(intent)
    startActivityForResult(intent, requestCode)
}

//
//fun Fragment.jumpForResult(cls: Class<*>, requestCode: Int, intentParam: IntentParam? = null) {
//    val intent = Intent(activity, cls)
//    intentParam?.applyParam(intent)
//    startActivityForResult(intent, requestCode)
//}

/////////////////////////////////////////////////////////
///////// 下面是软键盘相关的扩展
////////////////////////////////////////////////////////

/**
 * 切换状态，打开则关闭，关闭则打开
 */
fun Context.changeSoftKeyboard() {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    if (!im.isActive)
        return
    im.toggleSoftInput(0, 0)
}

/**
 * 隐藏软键盘
 */
fun Context.hideSoftKeyboard(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (!im.isActive)
        return
    //隐藏软键盘 //
    im.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showSoftKeyboard(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (im.isActive)
        return
    im.showSoftInputFromInputMethod(view.windowToken, 0)
}


fun Context.showSelectDateDialog(
    @StyleRes style: Int = android.R.style.Theme_DeviceDefault_Dialog,
    listener: (View, Int, Int, Int) -> Unit =
        { view, year, month, dayOfMonth -> }
) {
    val c = Calendar.getInstance()
    DatePickerDialog(
        this, style, listener, c.get(Calendar.YEAR),
        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    )
        .show()
}

fun Context.showSelectDateDialog(
    @StyleRes style: Int = android.R.style.Theme_DeviceDefault_Dialog,
    listener: (Long) -> Unit =
        { time -> }
) {
    val c = Calendar.getInstance()
    DatePickerDialog(
        this, style, { view, year, month, dayOfMonth ->
            c.timeInMillis = 0
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            listener(c.timeInMillis)
        }, c.get(Calendar.YEAR),
        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    )
        .show()
}


fun Context.checkMyPermission(permission: String, granted: () -> Unit = {}, deny: () -> Unit = {}) {
    if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
        granted()
    } else {
        deny()
    }
}


/**
 * 不够位数的在前面补0，保留num的长度位数字
 * @param code
 * @return
 */
private fun autoGenericCode(code: String, num: Int): String {
    return String.format("%0" + num + "d", Integer.parseInt(code))
}

