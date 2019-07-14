package cn.qjm253.quick_android_custom_view.tin_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cn.qjm253.quick_android_custom_view.R
import cn.qjm253.quick_android_custom_view.custom_drawable_size_view.DrawableEditText

/**
 * 这是一个 DrawableEditText 的封装组件，在 DrawableEditText 的基础上支持设置drawable的tinColor
 *
 * @see DrawableEditText
 * @author SunnyQjm
 * @date 19-7-10 下午10:15
 */

class TinEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : DrawableEditText(context, attrs, defStyleAttr), View.OnFocusChangeListener {


    companion object {
        const val NONE = 0x0000
        const val LEFT = 0x0001
        const val TOP = 0x0002
        const val RIGHT = 0x0004
        const val BOTTOM = 0x0008
        const val ALL = 0x000f
    }


    private var tints: Int = NONE

    private var tinColor: ColorStateList? = null
    private var focusedTinColor: ColorStateList? = null

    private lateinit var mDrawables: Array<Drawable?>

    init {
        if (!isInEditMode) {
            mDrawables = compoundDrawables
            loadFromAttribute(context, attrs, defStyleAttr)
            this.onFocusChangeListener = this
        }
    }

    /**
     * 从布局文件中加载属性配置
     */
    private fun loadFromAttribute(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.TinEditText)
            tints = ta.getInt(R.styleable.TinEditText_tints, ALL)
            tinColor = ta.getColorStateList(R.styleable.TinEditText_tinColor)
            focusedTinColor = ta.getColorStateList(R.styleable.TinEditText_focusedTinColor)
            ta.recycle()
        }

        // 根绝当前是否聚焦，修改tinColor
        onFocusChange(this, isFocused)
    }

    /**
     * 设置哪些方向的drawable需要设置tinColor
     *
     * @see TinEditText.NONE     ->    四个方向都不着色
     * @see TinEditText.LEFT     ->    左边drawable着色
     * @see TinEditText.RIGHT    ->    右边drawable着色
     * @see TinEditText.TOP      ->    顶部drawable着色
     * @see TinEditText.BOTTOM   ->    底部drawable着色
     * @see TinEditText.ALL      ->    四个方向都着色
     *
     * 如果需要单独设置两个方向可以 => TinEditText.LEFT or TinEditText.RIGHT
     *
     */
    fun updateTints(tints: Int) {
        if (tints < 0 || tints > 8)
            return
        this.tints = tints
    }

    /**
     * 设置tinColor的颜色
     */
    private fun updateDrawablesTinColor(colorStateList: ColorStateList?) {
        colorStateList?.let {
            //设置icon
            if (mDrawables[0] != null && (tints and LEFT) == LEFT) {
                mDrawables[0] = DrawableCompat.wrap(mDrawables[0]!!.mutate())
                DrawableCompat.setTintList(mDrawables[0]!!, colorStateList)
            }

            if (mDrawables[1] != null && (tints and TinTextView.TOP) == TinTextView.TOP) {
                mDrawables[1] = DrawableCompat.wrap(mDrawables[1]!!.mutate())
                DrawableCompat.setTintList(mDrawables[1]!!, colorStateList)
            }

            if (mDrawables[2] != null && (tints and RIGHT) == RIGHT) {
                mDrawables[2] = DrawableCompat.wrap(mDrawables[2]!!.mutate())
                DrawableCompat.setTintList(mDrawables[2]!!, colorStateList)
            }

            if (mDrawables[3] != null && (tints and BOTTOM) == BOTTOM) {
                mDrawables[3] = DrawableCompat.wrap(mDrawables[3]!!.mutate())
                DrawableCompat.setTintList(mDrawables[3]!!, colorStateList)
            }
            setCompoundDrawables(mDrawables[0], mDrawables[1], mDrawables[2], mDrawables[3])
        }
    }


    /**
     * 设置tinColor的颜色
     */
    fun updateDrawablesTinColor(@ColorRes color: Int) {
        this.tinColor = ContextCompat.getColorStateList(context, color)
        onFocusChange(this, isFocused)
    }


    /**
     * 更新聚焦状态下 drawable 的颜色
     */
    fun updateDrawablesFocusedTinColor(@ColorRes color: Int) {
        this.focusedTinColor = ContextCompat.getColorStateList(context, color)
        onFocusChange(this, isFocused)
    }


    /**
     * 处理EditText焦点变化的时候修改 drawable 的 tinColor
     */
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            updateDrawablesTinColor(focusedTinColor)
        } else {
            updateDrawablesTinColor(tinColor)
        }
    }
}