package cn.qjm253.quick_android.mvp_demo

import cn.qjm253.quick_android_mvp.model.inernet.QuickAndroidMVPAPIManager
import io.reactivex.Observable

class MVPDemoActivityModel(val mPresenter: MVPDemoActivityPresenter) : MVPDemoActivityContract.Model {
    override fun getWeather(city: String): Observable<Weather> {
        return QuickAndroidMVPAPIManager.getService(TestServices::class.java)
            .getWeather(city)
    }

    init {
    }
}