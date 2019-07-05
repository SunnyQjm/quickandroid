package cn.qjm253.quick_android_base.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import cn.qjm253.quick_android_base.util.SpannableUtil

@SuppressLint("ResourceType")
fun TextView.setStyleText(text: String = this.text.toString(), @StringRes textRes: Int = -1,
                          isBold: Boolean = false, isItalic: Boolean = false, @ColorInt color: Int = -1,
                          @ColorRes colorRes: Int = -1) {
    val t = if (textRes > 0)
        context.getString(textRes)
    else
        text
    var result: CharSequence = t
    if (isBold)
        result = SpannableUtil.bold(result)
    if (isItalic)
        result = SpannableUtil.italic(result)
    if (color > 0 || colorRes > 0)
        result = SpannableUtil.color(if (colorRes > 0) resources.getColor(colorRes) else color, result)
    this.text = result
}


fun TextView.showHtml(html: String) {
    this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(html)
    }
}