package cn.qjm253.quick_android_image_picker.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.RelativeLayout
import cn.qjm253.quick_android_base.util.DisplayUtils
import kotlin.math.roundToInt


/**
 * @author fucker
 */
class ClipImageLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private val mZoomImageView: ClipZoomImageView = ClipZoomImageView(context)
    private val mClipImageView: ClipImageBorderView = ClipImageBorderView(context)

    private var mHorizontalPadding = 24
    private var aspectRatio = 1f

    init {

        val lp = LayoutParams(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )

        this.addView(mZoomImageView, lp)
        this.addView(mClipImageView, lp)

        setHorizontalPadding(mHorizontalPadding)
        setAspectRatio(aspectRatio)
    }

    fun setImageBitmap(bm: Bitmap) {
        mZoomImageView.setImageBitmap(bm)
    }

    fun setImagePath(path: String) {
        val screen = DisplayUtils.getScreenParams(context)
        val width = screen[0]
        val height = screen[1]

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        var scale = (if (options.outWidth * 1.0 / width < 1)
            1f
        else
            options.outWidth * 1.0f / width)
        val scale2 = (if (options.outHeight * 1.0 / height < 1)
            1f
        else
            options.outHeight * 1.0f / height)
        scale = if (scale < scale2) scale2 else scale//select a bigger scale number.
        options.outHeight = (options.outHeight / scale).toInt()
        options.outWidth = (options.outWidth / scale).toInt()
        options.inSampleSize = scale.roundToInt()
        options.inJustDecodeBounds = false
        val bm = BitmapFactory.decodeFile(path, options)
        setImageBitmap(bm)
    }

    /**
     * 设置左右边距的方法
     *
     * @param mHorizontalPadding dp
     */
    fun setHorizontalPadding(mHorizontalPadding: Int) {
        var mHorizontalPadding = mHorizontalPadding
        this.mHorizontalPadding = mHorizontalPadding
        // 计算padding的px
        mHorizontalPadding = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding.toFloat(), resources
                .displayMetrics
        ).toInt()
        mZoomImageView.setHorizontalPadding(mHorizontalPadding)
        mClipImageView.setHorizontalPadding(mHorizontalPadding)
    }

    /**
     * 设置裁剪图像的纵横比
     *
     * @param aspectRatio = height / width
     */
    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
        mZoomImageView.setAspectRatio(aspectRatio)
        mClipImageView.setAspectRatio(aspectRatio)
    }

    /**
     * 裁切图片,multiple  0~1.0f
     *
     * @return
     */
    fun clip(multiple: Float): Bitmap {
        return mZoomImageView.clip(multiple)
    }

}