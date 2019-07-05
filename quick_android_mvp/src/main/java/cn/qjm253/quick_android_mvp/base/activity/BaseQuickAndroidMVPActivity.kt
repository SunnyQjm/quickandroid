package cn.qjm253.quick_android_mvp.base.activity

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import cn.qjm253.quick_android_base.extensions.toast
import cn.qjm253.quick_android_mvp.base.mvp.BasePresenter
import cn.qjm253.quick_android_mvp.base.mvp.BaseView
import cn.qjm253.quick_android_mvp.exceptions.MVPExceptionWrapper
import com.orhanobut.logger.Logger
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * MVP 模式的Activity基类
 */
abstract class BaseQuickAndroidMVPActivity<P : BasePresenter<*, *>> : BaseQuickAndroidActivity(), BaseView {

    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = onCreatePresenter()
    }


    override fun doToast(res: Int, duration: Int) {
        toast(res, duration)
    }

    override fun doToast(message: String, duration: Int) {
        toast(message, duration)
    }


    override fun onError(exception: MVPExceptionWrapper) {
        // 打印错误
        Logger.e(exception.originException, exception.originException.message ?: "")
    }


    override fun showLoading() {
    }

    override fun hideLoading() {
    }


    /**
     * RxJava订阅绑定到Activity的生命周期中，触发onDestroy生命周期函数的时候自动取消订阅
     */
    override fun <T> bindAutoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable<T>(
            AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY)
        )
    }


    /**
     * 创建Presenter的接口，每个实现本类的Activity应该都要实现本方法，返回对应的Presenter
     */
    abstract fun onCreatePresenter(): P


}