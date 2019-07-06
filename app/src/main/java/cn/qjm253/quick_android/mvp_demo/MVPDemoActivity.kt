package cn.qjm253.quick_android.mvp_demo

import android.os.Bundle
import cn.qjm253.quick_android.R
import cn.qjm253.quick_android_mvp.base.activity.BaseQuickAndroidMVPActivity

class MVPDemoActivity : BaseQuickAndroidMVPActivity<MVPDemoActivityPresenter>(),
    MVPDemoActivityContract.View{
    override fun onCreatePresenter(): MVPDemoActivityPresenter =
        MVPDemoActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvpdemo)
    }
}
