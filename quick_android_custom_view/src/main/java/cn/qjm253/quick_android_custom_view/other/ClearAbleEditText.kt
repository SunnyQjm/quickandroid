package cn.qjm253.quick_android_custom_view.other

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cn.qjm253.quick_android_custom_view.tin_view.TinEditText

/**
 * 这是一个可以显示清空按钮的EditText组件，right_drawable一般设置成有清空含义的图标
 *
 * @author SunnyQjm
 * @date 19-7-14 下午8:01
 */

class ClearAbleEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : TinEditText(context, attrs, defStyleAttr) {


    init {
        changeDrawables(right = null)
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length ?: 0 > 0) {
                    changeDrawables(right = mDrawables[2])
                } else {
                    changeDrawables(right = null)
                }
            }

        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        super.onFocusChange(v, hasFocus)
        if (!hasFocus || text?.isEmpty() == true)
            changeDrawables(right = null)
        else
            changeDrawables(right = mDrawables[2])
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            //getTotalPaddingRight()图标左边缘至控件右边缘的距离
            //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
            //getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
            val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight

            if (touchable) {
                this.setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    private fun changeDrawables(
        left: Drawable? = mDrawables[0],
        top: Drawable? = mDrawables[1],
        right: Drawable? = mDrawables[2],
        bottom: Drawable? = mDrawables[3]
    ) {
        setCompoundDrawables(left, top, right, bottom)
    }
}