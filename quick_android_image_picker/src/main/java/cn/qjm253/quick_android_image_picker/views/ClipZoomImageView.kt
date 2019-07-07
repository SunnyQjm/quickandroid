package cn.qjm253.quick_android_image_picker.views


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

/**
 * @author fucker
 */
@SuppressLint("ClickableViewAccessibility")
class ClipZoomImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs), ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener,
    ViewTreeObserver.OnGlobalLayoutListener {

    private var initScale = 0.5f
    private var once = true

    private val matrixValues = FloatArray(9)

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private val mScaleMatrix = Matrix()

    /**
     */
    private val mGestureDetector: GestureDetector
    private var isAutoScale: Boolean = false

    private val mTouchSlop: Int = 0

    private var mLastX: Float = 0.toFloat()
    private var mLastY: Float = 0.toFloat()

    private var isCanDrag: Boolean = false
    private var lastPointerCount: Int = 0

    private val matrixRectF: RectF
        get() {
            val matrix = mScaleMatrix
            val rect = RectF()
            val d = drawable
            if (null != d) {
                rect.set(0f, 0f, d.intrinsicWidth.toFloat(), d.intrinsicHeight.toFloat())
                matrix.mapRect(rect)
            }
            return rect
        }

    val scale: Float
        get() {
            mScaleMatrix.getValues(matrixValues)
            return matrixValues[Matrix.MSCALE_X]
        }

    private var mHorizontalPadding: Int = 0
    private var mVerticalPadding: Int = 0
    /**
     * 裁剪框的纵横比，mVerticalPadding通过该值计算
     * 默认是正方形，所以等于1
     */
    private var aspectRatio = 1f

    init {

        scaleType = ScaleType.MATRIX
        mGestureDetector = GestureDetector(context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    if (isAutoScale == true)
                        return true

                    val x = e.x
                    val y = e.y
                    if (scale < SCALE_MID) {
                        this@ClipZoomImageView.postDelayed(
                            AutoScaleRunnable(SCALE_MID, x, y), 16
                        )
                        isAutoScale = true
                    } else {
                        this@ClipZoomImageView.postDelayed(
                            AutoScaleRunnable(initScale, x, y), 16
                        )
                        isAutoScale = true
                    }

                    return true
                }
            })
        mScaleGestureDetector = ScaleGestureDetector(context, this)
        this.setOnTouchListener(this)
    }

    fun setInitScale(scale: Float) {
        if (drawable != null) {
            this.initScale = scale
        }
    }

    /**
     * @author zhy
     */
    private inner class AutoScaleRunnable
    /**
     * @param targetScale
     */
        (private val mTargetScale: Float, private val x: Float, private val y: Float) : Runnable {
        private var tmpScale: Float = 0.toFloat()

        init {
            if (scale < mTargetScale) {
                tmpScale = 1.07f
            } else {
                tmpScale = 0.93f
            }

        }

        override fun run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y)
            checkBorder()
            imageMatrix = mScaleMatrix

            val currentScale = scale
            if (tmpScale > 1f && currentScale < mTargetScale || tmpScale < 1f && mTargetScale < currentScale) {
                this@ClipZoomImageView.postDelayed(this, 16)
            } else {
                val deltaScale = mTargetScale / currentScale
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y)
                checkBorder()
                imageMatrix = mScaleMatrix
                isAutoScale = false
            }

        }

    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val scale = scale
        var scaleFactor = detector.scaleFactor

        if (drawable == null)
            return true

        if (scale < SCALE_MAX && scaleFactor > 1.0f || scale > initScale && scaleFactor <= 1.0f) {
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale
            }
            mScaleMatrix.postScale(
                scaleFactor, scaleFactor,
                detector.focusX, detector.focusY
            )
            checkBorder()
            imageMatrix = mScaleMatrix
        }
        return true

    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onScaleEnd(detector: ScaleGestureDetector) {
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        if (mGestureDetector.onTouchEvent(event))
            return true
        mScaleGestureDetector!!.onTouchEvent(event)

        var x = 0f
        var y = 0f
        val pointerCount = event.pointerCount
        for (i in 0 until pointerCount) {
            x += event.getX(i)
            y += event.getY(i)
        }
        x /= pointerCount
        y /= pointerCount

        if (pointerCount != lastPointerCount) {
            isCanDrag = false
            mLastX = x
            mLastY = y
        }

        lastPointerCount = pointerCount
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                var dx = x - mLastX
                var dy = y - mLastY

                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy)
                }
                if (isCanDrag) {
                    if (drawable != null) {

                        val rectF = matrixRectF

                        if (rectF.width() <= width - mHorizontalPadding * 2) {
                            dx = 0f
                        }
                        if (rectF.height() <= height - mVerticalPadding * 2) {
                            dy = 0f
                        }
                        mScaleMatrix.postTranslate(dx, dy)
                        checkBorder()
                        imageMatrix = mScaleMatrix
                    }
                }
                mLastX = x
                mLastY = y
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> lastPointerCount = 0
        }

        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeGlobalOnLayoutListener(this)
    }

    override fun onGlobalLayout() {
        if (once) {
            val d = drawable ?: return
            mVerticalPadding = ((height - aspectRatio * (width - 2 * mHorizontalPadding)) / 2).toInt()

            val width = width
            val height = height

            val dw = d.intrinsicWidth
            val dh = d.intrinsicHeight
            var scale = 1.0f
            if (dw < getWidth() - mHorizontalPadding * 2 && dh > getHeight() - mVerticalPadding * 2) {
                scale = (getWidth() * 1.0f - mHorizontalPadding * 2) / dw
            }

            if (dh < getHeight() - mVerticalPadding * 2 && dw > getWidth() - mHorizontalPadding * 2) {
                scale = (getHeight() * 1.0f - mVerticalPadding * 2) / dh
            }

            if (dw < getWidth() - mHorizontalPadding * 2 && dh < getHeight() - mVerticalPadding * 2) {
                val scaleW = (getWidth() * 1.0f - mHorizontalPadding * 2) / dw
                val scaleH = (getHeight() * 1.0f - mVerticalPadding * 2) / dh
                scale = Math.max(scaleW, scaleH)
            }
            if (dw >= getWidth() - mHorizontalPadding * 2 && dh >= getHeight() - mVerticalPadding * 2) {
                val scaleW = (getWidth() * 1.0f - mHorizontalPadding * 2) / dw
                val scaleH = (getHeight() * 1.0f - mVerticalPadding * 2) / dh
                scale = Math.max(scaleW, scaleH)
            }

            initScale = scale
            SCALE_MID = initScale * 2
            SCALE_MAX = initScale * 4
            mScaleMatrix.postTranslate(((width - dw) / 2).toFloat(), ((height - dh) / 2).toFloat())
            mScaleMatrix.postScale(
                scale, scale, (getWidth() / 2).toFloat(),
                (getHeight() / 2).toFloat()
            )

            imageMatrix = mScaleMatrix
            once = false
        }

    }

    fun clip(multiple: Float): Bitmap {
        var multiple = multiple
        multiple = if (multiple <= 0) 1.0f else multiple
        multiple = if (multiple > 1) 1.0f else multiple
        val width = width
        val height = height
        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        draw(canvas)
        val matrix = Matrix()
        matrix.postScale(multiple, multiple)
        return Bitmap.createBitmap(
            bitmap, mHorizontalPadding,
            mVerticalPadding, width - 2 * mHorizontalPadding, height - 2 * mVerticalPadding, matrix, true
        )
    }

    private fun checkBorder() {

        val rect = matrixRectF
        var deltaX = 0f
        var deltaY = 0f

        val width = width
        val height = height
        if (rect.width() + 0.01 >= width - 2 * mHorizontalPadding) {
            if (rect.left > mHorizontalPadding) {
                deltaX = -rect.left + mHorizontalPadding
            }
            if (rect.right < width - mHorizontalPadding) {
                deltaX = width.toFloat() - mHorizontalPadding.toFloat() - rect.right
            }
        }
        if (rect.height() + 0.01 >= height - 2 * mVerticalPadding) {
            if (rect.top > mVerticalPadding) {
                deltaY = -rect.top + mVerticalPadding
            }
            if (rect.bottom < height - mVerticalPadding) {
                deltaY = height.toFloat() - mVerticalPadding.toFloat() - rect.bottom
            }
        }
        mScaleMatrix.postTranslate(deltaX, deltaY)

    }

    private fun isCanDrag(dx: Float, dy: Float): Boolean {
        return sqrt((dx * dx + dy * dy).toDouble()) >= mTouchSlop
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

    companion object {

        var SCALE_MAX = 4.0f
        private var SCALE_MID = 2.0f
    }

}