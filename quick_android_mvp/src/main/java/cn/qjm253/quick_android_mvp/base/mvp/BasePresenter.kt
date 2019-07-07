package cn.qjm253.quick_android_mvp.base.mvp

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
abstract class BasePresenter<V: BaseView, M: BaseModel> {
    protected lateinit var mView: V
    protected lateinit var mModel: M
}