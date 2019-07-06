package cn.qjm253.quick_android_mvp.base.mvp

abstract class BasePresenter<V: BaseView, M: BaseModel> {
    protected lateinit var mView: V
    protected lateinit var mModel: M
}