package cn.qjm253.quick_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.qjm253.quick_android.mvp_demo.MVPDemoActivity
import cn.qjm253.quick_android_base.extensions.jumpTo
import cn.qjm253.quick_android_qrcode.activity.QuickAndroidQrCodeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpDemoBtn.setOnClickListener {
            jumpTo(MVPDemoActivity::class.java)
        }

        btnScanQrCode.setOnClickListener {
            jumpTo(QuickAndroidQrCodeActivity::class.java)
        }
    }
}
