package cn.qjm253.quick_android.mvp_demo

import cn.qjm253.quick_android_base.extensions.toJson
import cn.qjm253.quick_android_mvp.model.inernet.rx.RxSchedulersHelper
import cn.qjm253.quick_android_mvp.model.inernet.rx.qaHandleResult
import cn.qjm253.quick_android_mvp.model.inernet.rx.qaSubscribe
import cn.qjm253.quick_android_mvp.model.response.BaseQuickAndroidResponseBody
import com.orhanobut.logger.Logger
import io.reactivex.Observable

class MVPDemoActivityPresenter(mvpDemoActivity: MVPDemoActivity) : MVPDemoActivityContract.Presenter() {
    override fun testSuccess() {
        Observable.just(BaseQuickAndroidResponseBody(0))
            .qaHandleResult()
            .qaSubscribe(mView, {

            })
    }

    override fun testError() {
        Observable.just(BaseQuickAndroidResponseBody(-1))
            .qaHandleResult()
            .qaSubscribe(mView, {

            })
    }

    override fun testRetryWhen() {
        Observable.just(BaseQuickAndroidResponseBody(-2))
            .qaHandleResult()
            .qaSubscribe(mView, {

            })
    }

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