package cn.qjm253.quick_android.mvp_demo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import cn.qjm253.quick_android.R
import cn.qjm253.quick_android_base.extensions.toJson
import cn.qjm253.quick_android_mvp.base.activity.BaseQuickAndroidMVPActivity
import kotlinx.android.synthetic.main.activity_mvpdemo.*

class MVPDemoActivity : BaseQuickAndroidMVPActivity<MVPDemoActivityPresenter>(),
    MVPDemoActivityContract.View{
    override fun displayWeather(w: Weather) {
        tvResult.post {
            tvResult.text = w.toJson()
        }
    }

    override fun onCreatePresenter(): MVPDemoActivityPresenter =
        MVPDemoActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpdemo)

        btnQuery.setOnClickListener {
            val city = etCity.text.toString()
            mPresenter.getWeather(city)
        }
    }
}
