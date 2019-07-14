package cn.qjm253.quick_android_custom_view.tin_view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cn.qjm253.quick_android_custom_view.R

/**
 * 这是一个 DrawableButton 的封装组件，在AppCompatImageView的基础上支持设置drawable的tinColor
 *
 * @author SunnyQjm
 * @date 19-7-11 下午4:46
 */

class TinImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var mDrawable = drawable

    init {
        loadFromAttribute(context, attrs, defStyleAttr)
    }

    /**
     * 从布局文件中加载属性配置
     */
    private fun loadFromAttribute(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.TinImageView)
            ta.getColorStateList(R.styleable.TinImageView_tinColor)?.let { tinColorStateList ->
                updateDrawablesTinColor(tinColorStateList)
            }
            ta.recycle()
        }
    }

    /**
     * 设置tinColor的颜色
     */
    fun updateDrawablesTinColor(@ColorRes color: Int) {
        updateDrawablesTinColor(ContextCompat.getColorStateList(context, color))
    }

    /**
     * 设置tinColor的颜色
     */
    private fun updateDrawablesTinColor(colorStateList: ColorStateList?) {
        colorStateList?.let {
            mDrawable = DrawableCompat.wrap(mDrawable.mutate())
            DrawableCompat.setTintList(mDrawable, colorStateList)
            setImageDrawable(mDrawable)
        }
    }
}