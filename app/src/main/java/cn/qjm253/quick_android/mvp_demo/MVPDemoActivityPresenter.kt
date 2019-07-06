package cn.qjm253.quick_android.mvp_demo

import cn.qjm253.quick_android_base.extensions.toJson
import cn.qjm253.quick_android_mvp.model.inernet.rx.RxSchedulersHelper
import cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
import com.orhanobut.logger.Logger

class MVPDemoActivityPresenter(mvpDemoActivity: MVPDemoActivity) : MVPDemoActivityContract.Presenter() {
    override fun getWeather(city: String) {
        val res = mModel.getWeather(city)
            .compose(RxSchedulersHelper.io_main())
            .qaSubscribe(mView, {
                mView.displayWeather(it)
                Logger.i("next： ${it.toJson()}")
                Logger.json(it.toJson())
            })
    }

    init {
        mView = mvpDemoActivity
        mModel = MVPDemoActivityModel(this)
    }
}