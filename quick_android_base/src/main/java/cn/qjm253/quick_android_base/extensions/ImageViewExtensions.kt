package cn.qjm253.quick_android_base.extensions

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.annotation.DrawableRes
import android.widget.ImageView
import cn.qjm253.quick_android_base.GlideApp
import com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */


fun ImageView.load(url: String) {
    GlideApp.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadCircle(url: String) {
    GlideApp.with(this)
            .load(url)
            .circleCrop()
            .into(this)
}

fun ImageView.loadCircle(@DrawableRes res: Int) {
    GlideApp.with(this)
            .load(res)
            .circleCrop()
            .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadVideoFrame(url: String, frameTimeMicros: Long) {
    val requestOptions = RequestOptions.frameOf(frameTimeMicros)
    requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
    requestOptions.transform(object : BitmapTransformation() {
        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
            return toTransform
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
            try {
                messageDigest.update((context.packageName + "RotateTransform").toByteArray(charset("utf-8")))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    })
    GlideApp.with(this)
        .load(url)
        .apply(requestOptions)
        .into(this)
}