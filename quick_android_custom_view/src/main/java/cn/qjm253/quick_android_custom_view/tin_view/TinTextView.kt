package cn.qjm253.quick_android_custom_view.tin_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cn.qjm253.quick_android_custom_view.R
import cn.qjm253.quick_android_custom_view.custom_drawable_size_view.DrawableTextView

/**
 * 这是一个 DrawableTextView 的封装组件，在DrawableTextView的基础上支持设置drawable的tinColor
 *
 * @see DrawableTextView
 * @author SunnyQjm
 * @date 19-7-10 下午9:12
 */

class TinTextView constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle
) : DrawableTextView(context, attrs, defStyleAttr) {
    companion object {
        const val NONE = 0x0000
        const val LEFT = 0x0001
        const val TOP = 0x0002
        const val RIGHT = 0x0004
        const val BOTTOM = 0x0008
        const val ALL = 0x000f
    }


    private var tints: Int = NONE
    private lateinit var mDrawables: Array<Drawable?>

    init {
        if (!isInEditMode) {
            mDrawables = compoundDrawables
            loadFromAttribute(context, attrs, defStyleAttr)
        }
    }

    /**
     * 从布局文件中加载属性配置
     */
    private fun loadFromAttribute(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.TinTextView)
            tints = ta.getInt(R.styleable.TinTextView_tints, NONE)
            ta.getColorStateList(R.styleable.TinTextView_tinColor)?.let { tinColorStateList ->
                updateDrawablesTinColor(tinColorStateList)
            }
            ta.recycle()
        }
    }


    /**
     * 设置哪些方向的drawable需要设置tinColor
     *
     * @see TinTextView.NONE     ->    四个方向都不着色
     * @see TinTextView.LEFT     ->    左边drawable着色
     * @see TinTextView.RIGHT    ->    右边drawable着色
     * @see TinTextView.TOP      ->    顶部drawable着色
     * @see TinTextView.BOTTOM   ->    底部drawable着色
     * @see TinTextView.ALL      ->    四个方向都着色
     *
     * 如果需要单独设置两个方向可以 => TinTextView.LEFT or TinTextView.RIGHT
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
    private fun updateDrawablesTinColor(colorStateList: ColorStateList) {
        //设置icon
        if (mDrawables[0] != null && (tints and LEFT) == LEFT) {
            mDrawables[0] = DrawableCompat.wrap(mDrawables[0]!!.mutate())
            DrawableCompat.setTintList(mDrawables[0]!!, colorStateList)
        }

        if (mDrawables[1] != null && (tints and TOP) == TOP) {
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


    /**
     * 设置tinColor的颜色
     */
    fun updateDrawablesTinColor(@ColorRes color: Int) {
        ContextCompat.getColorStateList(context, color)?.let {
            updateDrawablesTinColor(it)
        }
    }
}