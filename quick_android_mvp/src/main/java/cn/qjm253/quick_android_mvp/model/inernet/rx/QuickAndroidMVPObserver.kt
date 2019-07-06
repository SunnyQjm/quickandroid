package cn.qjm253.quick_android_mvp.model.inernet.rx

import androidx.annotation.CallSuper
import cn.qjm253.quick_android_mvp.QuickAndroidMVP
import cn.qjm253.quick_android_mvp.base.mvp.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class QuickAndroidMVPObserver<T>(private val mView: BaseView) : Observer<T> {

    @CallSuper
    override fun onComplete() {
        QuickAndroidMVP.onCompleteListeners.forEach {
            it()
        }
    }

    @CallSuper
    override fun onNext(t: T) {
        QuickAndroidMVP.onBeforeNextListeners.forEach {
            it()
        }

    }

    @CallSuper
    override fun onError(e: Throwable) {
        QuickAndroidMVP.onErrorListeners.forEach {
            it(e)
        }
        mView.onError(e)
    }

    @CallSuper
    override fun onSubscribe(d: Disposable) {
    }

}