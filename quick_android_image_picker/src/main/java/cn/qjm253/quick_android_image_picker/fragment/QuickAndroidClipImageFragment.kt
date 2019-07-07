package cn.qjm253.quick_android_image_picker.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import cn.qjm253.quick_android_base.extensions.e
import cn.qjm253.quick_android_base.extensions.i
import cn.qjm253.quick_android_base.params.IntentParam
import cn.qjm253.quick_android_base.util.FileUtils
import cn.qjm253.quick_android_base.util.RxSchedulersHelper
import cn.qjm253.quick_android_image_picker.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.android.synthetic.main.fragment_quick_android_clip_image.*
import java.io.File
import java.lang.Exception

/**
 * 图片裁剪Fragment
 *
 * @author SunnyQjm
 * @date 19-7-7 下午3:58
 */
class QuickAndroidClipImageFragment : BaseQuickAndroidFragment() {

    companion object {
        // 需要裁剪的原图片路径
        const val QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH = "QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH"

        // 压缩强度
        const val QUICK_ANDROID_CLIP_IMAGE_MULTIPLE = "QUICK_ANDROID_CLIP_IMAGE_MULTIPLE"

        // 裁剪后的图片路径
        const val QUICK_ANDROID_CLIP_IMAGE_RESULT = "QUICK_ANDROID_CLIP_IMAGE_RESULT"

        const val QUICK_ANDROID_CLIP_IMAGE_RW_PERMISSION_REQUEST_CODE = 1

        const val DEFAULT_MULTIPLE = 0.3f
        fun newInstance(path: String, multiple: Float = 0.3f): QuickAndroidClipImageFragment {
            return newInstance_(
                QuickAndroidClipImageFragment::class.java, IntentParam()
                    .add(QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH, path)
                    .add(QUICK_ANDROID_CLIP_IMAGE_MULTIPLE, multiple)
            )
        }
    }


    private var multiple: Float = DEFAULT_MULTIPLE

    override fun getRes(): Int = R.layout.fragment_quick_android_clip_image

    override fun initView() {
        val imagePath = arguments?.getString(QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH) ?: ""
        multiple = arguments?.getFloat(QUICK_ANDROID_CLIP_IMAGE_MULTIPLE, DEFAULT_MULTIPLE) ?: DEFAULT_MULTIPLE

        // 检查边界，限制在 0~1之间
        if (multiple < 0) {
            multiple = 0.1f
        } else if (multiple > 1f) {
            multiple = 1f
        }
        clipImageLayout.setImagePath(imagePath)
    }

    @SuppressLint("CheckResult")
    private fun doCLip_() {
        Observable.create { emitter: ObservableEmitter<File> ->
            try {
                FileUtils.generatePictureFile("clip.png").apply {
                    if (!exists())
                        createNewFile()
                    outputStream().use {
                        clipImageLayout.clip(multiple)
                            .compress(Bitmap.CompressFormat.PNG, 100, it)
                    }
                    emitter.onNext(this)
                    emitter.onComplete()
                }
            } catch (err: Exception) {
                emitter.onError(err)
            }
        }
            .compose(RxSchedulersHelper.io_main())
            .subscribe({ file ->
                Intent().let {
                    it.putExtra(QUICK_ANDROID_CLIP_IMAGE_RESULT, file.absolutePath)
                    activity?.setResult(0, it)
                    activity?.finish()
                }
                "${file.absolutePath} 压缩成功 （${file.length()}）".i()
            }, {
                it.e()
            })
    }

    fun doClip() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            R.string.clip_image_permission_tip, QUICK_ANDROID_CLIP_IMAGE_RW_PERMISSION_REQUEST_CODE
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsGranted(requestCode, perms)
        when (requestCode) {
            QUICK_ANDROID_CLIP_IMAGE_RW_PERMISSION_REQUEST_CODE -> {
                doCLip_()
            }
        }
    }

    override fun initialLoadData() {
    }

}