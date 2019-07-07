package cn.qjm253.quick_android_image_picker.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * @author fucker
 */
class ClipImageBorderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    View(context, attrs, defStyle) {
    /**
     * 水平方向与View的边距
     */
    private var mHorizontalPadding: Int = 0
    /**
     * 垂直方向与View的边距
     */
    private var mVerticalPadding: Int = 0
    /**
     * 裁剪框的纵横比，mVerticalPadding通过该值计算
     * 默认是正方形，所以等于1
     */
    private var aspectRatio = 1f
    /**
     * 绘制的矩形的宽度
     */
    private var mWidth: Int = 0
    /**
     * 边框的颜色，默认为白色
     */
    private val mBorderColor = Color.parseColor("#FFFFFF")
    /**
     * 边框的宽度单位dp
     */
    private var mBorderWidth = 1

    private val mPaint: Paint

    init {

        mBorderWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, mBorderWidth.toFloat(), resources
                .displayMetrics
        ).toInt()
        mPaint = Paint()
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 计算矩形区域的宽度
        mWidth = width - 2 * mHorizontalPadding
        // 计算距离屏幕垂直边界的边距
        mVerticalPadding = ((height - mWidth * aspectRatio) / 2).toInt()
        mPaint.color = Color.parseColor("#000000")
        mPaint.alpha = (255 * 0.48f).toInt()
        mPaint.style = Style.FILL
        // 绘制左边1
        canvas.drawRect(0f, 0f, mHorizontalPadding.toFloat(), height.toFloat(), mPaint)
        // 绘制右边2
        canvas.drawRect(
            (width - mHorizontalPadding).toFloat(), 0f, width.toFloat(),
            height.toFloat(), mPaint
        )
        // 绘制上边3
        canvas.drawRect(
            mHorizontalPadding.toFloat(), 0f, (width - mHorizontalPadding).toFloat(),
            mVerticalPadding.toFloat(), mPaint
        )
        // 绘制下边4
        canvas.drawRect(
            mHorizontalPadding.toFloat(), (height - mVerticalPadding).toFloat(),
            (width - mHorizontalPadding).toFloat(), height.toFloat(), mPaint
        )
        // 绘制外边距
        mPaint.color = mBorderColor
        mPaint.strokeWidth = mBorderWidth.toFloat()
        mPaint.style = Style.STROKE
        canvas.drawRect(
            mHorizontalPadding.toFloat(),
            mVerticalPadding.toFloat(),
            (width - mHorizontalPadding).toFloat(),
            (height - mVerticalPadding).toFloat(),
            mPaint
        )

    }

    fun setHorizontalPadding(mHorizontalPadding: Int) {
        this.mHorizontalPadding = mHorizontalPadding
    }

    /**
     * 设置clip框的纵横比
     *
     * @param aspectRatio
     */
    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
    }
}