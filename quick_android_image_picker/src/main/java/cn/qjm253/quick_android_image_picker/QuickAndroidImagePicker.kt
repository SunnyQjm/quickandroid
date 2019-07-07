package cn.qjm253.quick_android_image_picker

import android.content.Context
import cn.qjm253.quick_android_base.QuickAndroid
import cn.qjm253.quick_android_base.extensions.jumpTo
import cn.qjm253.quick_android_base.params.IntentParam
import cn.qjm253.quick_android_image_picker.activity.QuickAndroidClipImageActivity
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker.ui.ICustomPickerConfiguration
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder
import io.reactivex.Observable

/**
 * @author SunnyQjm
 * @date 19-7-7 下午2:25
 */
object QuickAndroidImagePicker {

    /**
     * 打开知乎样式的图片选择器
     */
    fun openZhihuStyleGallery(
        context: Context,
        config: ICustomPickerConfiguration = ZhihuConfigurationBuilder(MimeType.ofImage(), false)
            .maxSelectable(9)
            .countable(true)
            .spanCount(4)
            .theme(R.style.Zhihu_Normal)
            .build()
    ): Observable<Result> {
        return RxImagePicker
            .create(MyImagePicker::class.java)
            .openZhihuStyleGallery(context, config)
    }

    /**
     * 打开微信样式的图片选择器
     */
    fun openWechatStyleGallery(
        context: Context,
        config: ICustomPickerConfiguration = WechatConfigrationBuilder(MimeType.ofImage(), false)
            .maxSelectable(9)
            .countable(true)
            .spanCount(4)
            .build()
    ): Observable<Result> {
        return RxImagePicker
            .create(MyImagePicker::class.java)
            .openWechatStyleGallery(context, config)
    }


    /**
     * 打开摄像头拍摄
     */
    fun openCamera(context: Context): Observable<Result> {
        return RxImagePicker
            .create()
            .openCamera(context)
    }

    /**
     * 跳转到裁剪页面裁剪图片
     */
    fun startClipImage(context: Context, path: String, multiple: Float = 0.3f) {
        context.jumpTo(
            QuickAndroidClipImageActivity::class.java, IntentParam()
                .add(QuickAndroidClipImageActivity.QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH, path)
                .add(QuickAndroidClipImageActivity.QUICK_ANDROID_CLIP_IMAGE_MULTIPLE, multiple)
        )
    }

}

//////////////////////////////////////////////////////////
////////// QuickAndroid 扩展
//////////////////////////////////////////////////////////

fun QuickAndroid.openZhihuStyleGallery(
    context: Context,
    config: ICustomPickerConfiguration = ZhihuConfigurationBuilder(MimeType.ofImage(), false)
        .maxSelectable(9)
        .countable(true)
        .spanCount(4)
        .theme(R.style.Zhihu_Normal)
        .build()
): Observable<Result> {
    return QuickAndroidImagePicker.openZhihuStyleGallery(context, config)
}

fun QuickAndroid.openWechatStyleGallery(
    context: Context,
    config: ICustomPickerConfiguration = WechatConfigrationBuilder(MimeType.ofImage(), false)
        .maxSelectable(9)
        .countable(true)
        .spanCount(4)
        .build()
): Observable<Result> {
    return QuickAndroidImagePicker.openWechatStyleGallery(context, config)
}

fun QuickAndroid.openCamera(context: Context): Observable<Result> {
    return QuickAndroidImagePicker.openCamera(context)
}

fun QuickAndroid.startClipImage(context: Context, path: String, multiple: Float = 0.3f) {
    return QuickAndroidImagePicker.startClipImage(context, path, multiple)
}


//////////////////////////////////////////////////////////
////////// Context 扩展
//////////////////////////////////////////////////////////

fun Context.openZhihuStyleGallery(
    config: ICustomPickerConfiguration = ZhihuConfigurationBuilder(MimeType.ofImage(), false)
        .maxSelectable(9)
        .countable(true)
        .spanCount(4)
        .theme(R.style.Zhihu_Normal)
        .build()
): Observable<Result> {
    return QuickAndroidImagePicker.openZhihuStyleGallery(this, config)
}

fun Context.openWechatStyleGallery(
    config: ICustomPickerConfiguration = WechatConfigrationBuilder(MimeType.ofImage(), false)
        .maxSelectable(9)
        .countable(true)
        .spanCount(4)
        .build()
): Observable<Result> {
    return QuickAndroidImagePicker.openWechatStyleGallery(this, config)
}

fun Context.openCamera(): Observable<Result> {
    return QuickAndroidImagePicker.openCamera(this)
}

fun Context.startClipImage(path: String, multiple: Float = 0.3f) {
    return QuickAndroidImagePicker.startClipImage(this, path, multiple)
}