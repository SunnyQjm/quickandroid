package cn.qjm253.quick_android_mvp.model.inernet.rx

import io.reactivex.Observable


/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */

abstract class QuickAndroidMVPResultDeal (
    exceptionCodes: Array<Pair<Int, String>>,           // 错误的状态码对应表
    normalCodes: Array<Pair<Int, String>>,              // 除错误以外的状态码
    needRetryCodes: Array<Pair<Int, String>>            // 需要重试的状态码
) {

    enum class Type {
        TYPE_NORMAL, TYPE_ERROR, TYPE_ERROR_NEED_RETRY
    }

    class QuickAndroidMVPResultDealItem(
        val code: Int,
        val description: String,
        val type: Type
    )


    private val exceptionCodeMap: MutableMap<Int, String> = mutableMapOf(*exceptionCodes)
    private val normalCodeMap: MutableMap<Int, String> = mutableMapOf(*normalCodes)
    private val needRetryCodeMap: MutableMap<Int, String> = mutableMapOf(*needRetryCodes)


    fun getQuickAndroidMVPResultDealItem(code: Int): QuickAndroidMVPResultDealItem? {
        var item: QuickAndroidMVPResultDealItem? = null
        when {
            exceptionCodeMap.keys.contains(code) -> {
                item = QuickAndroidMVPResultDealItem(code, exceptionCodeMap[code] ?: "", Type.TYPE_ERROR)
            }
            normalCodeMap.keys.contains(code) -> {
                item = QuickAndroidMVPResultDealItem(code, normalCodeMap[code] ?: "", Type.TYPE_NORMAL)
            }
            needRetryCodeMap.keys.contains(code) -> {
                item = QuickAndroidMVPResultDealItem(code, needRetryCodeMap[code] ?: "", Type.TYPE_ERROR_NEED_RETRY)
            }
        }
        return item
    }


    /**
     * 正常状态码下的统一处理回调
     */
    abstract fun normalDeal(code: Int, description: String)


    /**
     * 错误状态码下的统一处理回调
     */
    abstract fun errorDeal(code: Int, description: String)

    /**
     * 处理后重试
     *
     * eg.: 应用场景 => Token失效之后重新获取Token，新的Token获取成功后重新发起原先失败的请求
     * @return 返回的Observable触发onComplete 或者 onError, 则不会重新订阅原Observable
     *         返回的Observable触发onNext，则会重新订阅原Observable
     */
    abstract fun  dealRetry(t: Throwable): Observable<Int>
}