package cn.qjm253.quick_android_mvp.base.fragment

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import cn.qjm253.quick_android_base.base.fragment.BaseQuickAndroidFragment
import cn.qjm253.quick_android_base.extensions.toast
import cn.qjm253.quick_android_mvp.base.mvp.BasePresenter
import cn.qjm253.quick_android_mvp.base.mvp.BaseView
import com.orhanobut.logger.Logger
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * MVP 模式的Fragment基类
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
abstract class BaseQuickAndroidMVPFragment<P: BasePresenter<*, *>>
    : BaseQuickAndroidFragment(), BaseView {



    protected lateinit var mPresenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = onCreatePresenter()
    }

    /**
     * 创建Presenter的接口，每个实现本类的Activity应该都要实现本方法，返回对应的Presenter
     */
    abstract fun onCreatePresenter(): P




    override fun onError(e: Throwable) {
        Logger.e(e, e.message ?: "")
    }

    override fun doToast(message: String, duration: Int) {
        context?.toast(message, duration)
    }

    override fun doToast(res: Int, duration: Int) {
        context?.toast(res, duration)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun <T> bindAutoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable<T>(
            AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
        )
    }
}