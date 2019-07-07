package cn.qjm253.quick_android_image_picker.activity

import android.os.Bundle
import cn.qjm253.quick_android_base.base.activity.BaseQuickAndroidActivity
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import cn.qjm253.quick_android_base.extensions.addFragmentToActivity
import cn.qjm253.quick_android_image_picker.R
import cn.qjm253.quick_android_image_picker.fragment.QuickAndroidClipImageFragment
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_quick_android_clip_image.*

class QuickAndroidClipImageActivity : BaseQuickAndroidActivity(), BaseQuickAndroidFragment.OnFragmentInteractionListener {

    companion object {
        const val QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH = QuickAndroidClipImageFragment.QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH
        const val QUICK_ANDROID_CLIP_IMAGE_MULTIPLE = QuickAndroidClipImageFragment.QUICK_ANDROID_CLIP_IMAGE_MULTIPLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .fullScreen(true)
            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            .init()
        setContentView(R.layout.activity_quick_android_clip_image)

        // 如果有的话隐藏ActionBar
        supportActionBar?.hide()

        val path = intent.getStringExtra(QUICK_ANDROID_CLIP_IMAGE_IMAGE_PATH)
        val multiple = intent.getFloatExtra(QUICK_ANDROID_CLIP_IMAGE_MULTIPLE, QuickAndroidClipImageFragment.DEFAULT_MULTIPLE)

        val fragment = addFragmentToActivity(R.id.quickAndroidClipImageFragmentContainer) {
            return@addFragmentToActivity QuickAndroidClipImageFragment.newInstance(path, multiple)
        }

        tvFinish.setOnClickListener {
            fragment.doClip()
        }
    }
}
