package cn.qjm253.quick_android_mvp.model.inernet.rx

import cn.qjm253.quick_android_mvp.QuickAndroidMVP
import cn.qjm253.quick_android_mvp.base.mvp.BaseView
import cn.qjm253.quick_android_mvp.exceptions.MVPAPIResultDealException
import cn.qjm253.quick_android_mvp.exceptions.MVPAPIResultDealNeedRetryException
import cn.qjm253.quick_android_mvp.model.response.BaseQuickAndroidResponseBody
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


/**
 * 网络请求结果统一处理
 */
fun <T: BaseQuickAndroidResponseBody> Observable<T>.qaHandleResult(): Observable<T> {
    return this.compose { upstream ->
        upstream.flatMap<T> {
            val code = it.code

            val item = QuickAndroidMVP.quickAndroidMVPResultDeal?.getQuickAndroidMVPResultDealItem(code)
            when (item?.type) {
                QuickAndroidMVPResultDeal.Type.TYPE_NORMAL -> {
                    QuickAndroidMVP.quickAndroidMVPResultDeal?.normalDeal(item.code, item.description)
                    return@flatMap Observable.just(it)
                }
                QuickAndroidMVPResultDeal.Type.TYPE_ERROR -> {
                    QuickAndroidMVP.quickAndroidMVPResultDeal?.errorDeal(item.code, item.description)
                    return@flatMap Observable.error(MVPAPIResultDealException(item.description))
                }
                QuickAndroidMVPResultDeal.Type.TYPE_ERROR_NEED_RETRY -> {
                    return@flatMap Observable.error(MVPAPIResultDealNeedRetryException(item.description))
                }
            }
            return@flatMap Observable.just(it)
        }.retryWhen { t: Observable<Throwable> ->
            t.flatMap {
                if(QuickAndroidMVP.quickAndroidMVPResultDeal != null &&
                    it is MVPAPIResultDealNeedRetryException) {
                    return@flatMap QuickAndroidMVP.quickAndroidMVPResultDeal?.dealRetry(it)
                }
                return@flatMap Observable.error<T>(it)
            }

        }
    }
}


/**
 * 网络请求订阅封装
 */
fun <T> Observable<T>.qaSubscribe(
    mView: BaseView,
    onNext_: (T) -> Unit,
    onError_: (Throwable) -> Unit = {},
    onComplete_: () -> Unit = {},
    onSubscribe_: (Disposable) -> Unit = {}
) {
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