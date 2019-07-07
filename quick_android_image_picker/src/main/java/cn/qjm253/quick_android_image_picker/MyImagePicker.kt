package cn.qjm253.quick_android_image_picker

import android.content.Context
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker.entity.sources.Camera
import com.qingmei2.rximagepicker.entity.sources.Gallery
import com.qingmei2.rximagepicker.ui.ICustomPickerConfiguration
import com.qingmei2.rximagepicker_extension_wechat.ui.WechatImagePickerActivity
import com.qingmei2.rximagepicker_extension_zhihu.ui.ZhihuImagePickerActivity
import io.reactivex.Observable

/**
 * @author SunnyQjm
 * @date 19-7-7 下午1:52
 */

interface MyImagePicker {

    /**
     * 打开类知乎的图片选择器
     */
    @Gallery(componentClazz = ZhihuImagePickerActivity::class, openAsFragment = false)
    fun openZhihuStyleGallery(context: Context,
                              config: ICustomPickerConfiguration): Observable<Result>

    /**
     * 打开类微信的图片选择器
     */
    @Gallery(componentClazz = WechatImagePickerActivity::class, openAsFragment = false)
    fun openWechatStyleGallery(context: Context,
                               config: ICustomPickerConfiguration): Observable<Result>

    @Camera
    fun openCamera(context: Context): Observable<Result>
}