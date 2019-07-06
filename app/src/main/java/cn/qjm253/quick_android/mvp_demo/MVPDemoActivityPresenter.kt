package cn.qjm253.quick_android.mvp_demo

class MVPDemoActivityPresenter(mvpDemoActivity: MVPDemoActivity) : MVPDemoActivityContract.Presenter() {
    init {
        mView = mvpDemoActivity
        mModel = MVPDemoActivityModel(this)
    }
}