package cn.qjm253.quick_android_base.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.anzewei.parallaxbacklayout.ParallaxBack
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout

/**
 * @author SunnyQjm
 * @date 19-7-7 上午8:43
 */
@ParallaxBack
abstract class BaseQuickAndroidActivity : AppCompatActivity() {


    /**
     * 是否需要支持侧滑返回
     */
    open fun isNeedScrollBack() = true


    /**
     * 侧滑是否响应全屏
     * true => 全屏滑动都会触发侧滑
     * false => 边缘滑动才会触发侧滑
     */
    open fun isParallaxBackLayoutFullScreen() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = ParallaxHelper.getParallaxBackLayout(this, true)

        // 设置侧滑的响应模式
        if(isParallaxBackLayoutFullScreen()) {
            layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL)
        } else {
            layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT)
        }

        // 设置是否允许触发侧滑返回
        if(isNeedScrollBack()) {
            layout.setEnableGesture(true)
        } else {
            layout.setEnableGesture(false)
        }

//        layout.setEdgeMode()
    }

    /**
     * 禁止侧滑返回
     */
    fun disableParallaxBack() {
        ParallaxHelper.getParallaxBackLayout(this)
            .setEnableGesture(false)
    }

    /**
     * 开启侧滑返回
     */
    fun enableParallaxBack() {
        ParallaxHelper.getParallaxBackLayout(this)
            .setEnableGesture(true)
    }
}