package cn.qjm253.quick_android_mvp.base.mvp

import android.widget.Toast
import androidx.annotation.StringRes
import cn.qjm253.quick_android_mvp.exceptions.MVPExceptionWrapper
import com.uber.autodispose.AutoDisposeConverter

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
interface BaseView {

    /**
     * 通用的错误回调，应在Activity或者Fragment中实现
     */
    fun onError(e: Throwable)

    /**
     * 显示吐司弹窗
     */
    fun doToast(message: String, duration: Int = Toast.LENGTH_SHORT)
    fun doToast(@StringRes res: Int, duration: Int = Toast.LENGTH_SHORT)


    /**
     * 现在正在加载状态
     */
    fun showLoading()

    /**
     * 取消正在加载状态
     */
    fun hideLoading()

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
    </T> */
    fun <T> bindAutoDispose(): AutoDisposeConverter<T>
}