package cn.qjm253.quick_android.mvp_demo

import cn.qjm253.quick_android_mvp.base.mvp.BaseModel
import cn.qjm253.quick_android_mvp.base.mvp.BasePresenter
import cn.qjm253.quick_android_mvp.base.mvp.BaseView

interface MVPDemoActivityContract {
    interface View : BaseView {

    }

    interface Model : BaseModel {

    }

    abstract class Presenter : BasePresenter<View, Model>() {

    }
}