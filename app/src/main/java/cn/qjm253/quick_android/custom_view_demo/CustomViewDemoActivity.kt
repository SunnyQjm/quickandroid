package cn.qjm253.quick_android.custom_view_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.qjm253.quick_android.R
import kotlinx.android.synthetic.main.activity_custom_view_demo.*

class CustomViewDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view_demo)

        initView()
    }

    private fun initView() {
        tinButton.updateDrawablesTinColor(R.color.colorPrimary)
    }
}
