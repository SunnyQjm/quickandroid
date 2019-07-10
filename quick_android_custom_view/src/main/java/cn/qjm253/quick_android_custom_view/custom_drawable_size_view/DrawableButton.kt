package cn.qjm253.quick_android_custom_view.custom_drawable_size_view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import cn.qjm253.quick_android_custom_view.R

/**
 * 这是一个Button的封装组件，支持自定义四个方向的drawable的大小
 *
 * 1. 使用 'left_drawable', 'right_drawable', 'top_drawable', 'bottom_drawable' 属性来设置四个方向的drawable资源
 *
 * 2. 使用 'drawable_width', 'drawable_height' 统一设置四个drawable的大小
 *
 * 3. 使用 'left_drawable_width', 'left_drawable_height', 'right_drawable_width', 'right_drawable_height',
 *    'top_drawable_width', 'top_drawable_height', 'bottom_drawable_width', 'bottom_drawable_height'
 *    来定制指定方向drawable的大小
 *
 * @author SunnyQjm
 * @date 19-7-10 下午9:09
 */


open class DrawableButton constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {
    companion object {
        const val DEFAULT_SIZE = 20
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DrawableButton)

        val width = ta.getDimensionPixelOffset(
            R.styleable.DrawableButton_drawable_width,
            DEFAULT_SIZE
        )
        val height = ta.getDimensionPixelOffset(
            R.styleable.DrawableButton_drawable_height,
            DEFAULT_SIZE
        )

        val sizeWrap = SizeWrap()

        val letfD = ta.getDrawable(R.styleable.DrawableButton_left_drawable)
            ?.apply {
                val lwidth = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_left_drawable_width,
                    DEFAULT_SIZE
                )
                val lheight = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_left_drawable_height,
                    DEFAULT_SIZE
                )
                sizeWrap.checkSize(width, height, lwidth, lheight)
                setBounds(0, 0, sizeWrap.width, sizeWrap.height)
            }
        val rightD = ta.getDrawable(R.styleable.DrawableButton_right_drawable)
            ?.apply {
                val lwidth = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_right_drawable_width,
                    DEFAULT_SIZE
                )
                val lheight = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_right_drawable_height,
                    DEFAULT_SIZE
                )
                sizeWrap.checkSize(width, height, lwidth, lheight)
                setBounds(0, 0, sizeWrap.width, sizeWrap.height)
            }
        val topD = ta.getDrawable(R.styleable.DrawableButton_top_drawable)
            ?.apply {
                val lwidth = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_top_drawable_width,
                    DEFAULT_SIZE
                )
                val lheight = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_top_drawable_height,
                    DEFAULT_SIZE
                )
                sizeWrap.checkSize(width, height, lwidth, lheight)
                setBounds(0, 0, sizeWrap.width, sizeWrap.height)
            }
        val bottomD = ta.getDrawable(R.styleable.DrawableButton_bottom_drawable)
            ?.apply {
                val lwidth = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_bottom_drawable_width,
                    DEFAULT_SIZE
                )
                val lheight = ta.getDimensionPixelOffset(
                    R.styleable.DrawableButton_bottom_drawable_height,
                    DEFAULT_SIZE
                )
                sizeWrap.checkSize(width, height, lwidth, lheight)
                setBounds(0, 0, sizeWrap.width, sizeWrap.height)
            }

        this.setCompoundDrawables(letfD, topD, rightD, bottomD)

        ta.recycle()
    }
}