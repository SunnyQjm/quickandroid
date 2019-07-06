package cn.qjm253.quick_android.mvp_demo

import cn.qjm253.quick_android_mvp.base.mvp.BaseModel
import cn.qjm253.quick_android_mvp.base.mvp.BasePresenter
import cn.qjm253.quick_android_mvp.base.mvp.BaseView
import io.reactivex.Observable

interface MVPDemoActivityContract {
    interface View : BaseView {
        fun displayWeather(w: Weather)
    }

    interface Model : BaseModel {
        fun getWeather(city: String): Observable<Weather>
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getWeather(city: String)
    }
}