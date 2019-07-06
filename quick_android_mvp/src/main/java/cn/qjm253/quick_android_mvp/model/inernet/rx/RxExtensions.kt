package cn.qjm253.quick_android_mvp.model.inernet.rx

import cn.qjm253.quick_android_mvp.base.mvp.BaseView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.qaSubscribe(
    mView: BaseView,
    onNext_: (T) -> Unit,
    onError_: (Throwable) -> Unit = {},
    onComplete_: () -> Unit = {},
    onSubscribe_: (Disposable) -> Unit = {}) {
    this.subscribe(object : QuickAndroidMVPObserver<T>(mView) {
        override fun onNext(t: T) {
            super.onNext(t)
            onNext_(t)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            onError_(e)
        }

        override fun onComplete() {
            super.onComplete()
            onComplete_()
        }
        override fun onSubscribe(d: Disposable) {
            super.onSubscribe(d)
            onSubscribe_(d)
        }
    })
}