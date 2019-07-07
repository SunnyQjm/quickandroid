package cn.qjm253.quick_android_qrcode.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import cn.qjm253.quick_android_base.extensions.addFragmentToActivity
import cn.qjm253.quick_android_qrcode.R
import cn.qjm253.quick_android_qrcode.fragment.QuickAndroidQrCodeFragment
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

class QuickAndroidQrCodeActivity : AppCompatActivity(), BaseQuickAndroidFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri?) {

    }

    companion object {
        const val QR_CODE_RESULT = QuickAndroidQrCodeFragment.QR_CODE_RESULT
        const val QR_CODE_ERROR = QuickAndroidQrCodeFragment.QR_CODE_ERROR
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .fullScreen(true)
            .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            .init()
        setContentView(R.layout.activity_quick_android_qr_code)

        // 如果有的话隐藏ActionBar
        supportActionBar?.hide()
        addFragmentToActivity(R.id.quickAndroidQrCodeContainer) {
            return@addFragmentToActivity QuickAndroidQrCodeFragment.newInstance()
        }
    }
}
