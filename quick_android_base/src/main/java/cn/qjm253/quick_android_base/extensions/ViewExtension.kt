package cn.qjm253.quick_android_base.extensions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View

/**
 * Created by sunny on 17-12-29.
 */

/*************************************************************************
 * ObjectAnimator生成
 **************************************************************************/

fun View.scaleXAnimation(vararg values: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, "scaleX", *values)
}

fun View.scaleYAnimation(vararg values: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, "scaleY", *values)
}

fun View.rotationAnimation(vararg values: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, "rotation", *values)
}

fun View.transXAnimation(vararg values: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, "translationX", *values)
}

fun View.transYAnimation(vararg values: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, "translationY", *values)
}

fun View.alphaAnimation(vararg values: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, "alpha", *values)
}
/*************************************************************************
 * 属性动画
 **************************************************************************/

/**
 * XY轴同比例缩放
 */
fun View.scaleXY(vararg values: Float, duration: Long = 500): View {
    val animatorSet = AnimatorSet()
    animatorSet.setDuration(duration)
            .play(this.scaleXAnimation(*values))
            .with(this.scaleYAnimation(*values))
    animatorSet.start()
    return this
}



/**
 * 旋转
 */
fun View.rotate(vararg values: Float, duration: Long = 500, repeatCount: Int = 0,
                 repeatMode: Int = ValueAnimator.RESTART): ObjectAnimator {
    val animation = this.rotationAnimation(*values)
    with(animation
            .setDuration(duration)){
        this.repeatCount = repeatCount
        this.repeatMode = repeatMode
        start()
    }
    return animation
//    return this
}

/**
 * 平移
 */
fun View.transXY(transX: FloatArray = floatArrayOf(0f, 0f), transY: FloatArray = floatArrayOf(0f, 0f)
                 , duration: Long = 500): View {
    val animatorSet = AnimatorSet()
    animatorSet.setDuration(duration)
            .play(this.transXAnimation(*transX))
            .with(this.transYAnimation(*transY))
    animatorSet
            .start()
    return this
}

fun View.doAnim(floats: FloatArray = floatArrayOf(0f, 0f), callback: (value: Float) -> Unit = {}) {
    val objAnim = ObjectAnimator.ofFloat(*floats)
    objAnim.addUpdateListener {
        val value = it.animatedValue as Float
        callback(value)
    }
    objAnim.start()
}

/**
 * 多个属性动画并行运行
 */
fun View.animer(vararg animations: ObjectAnimator, duration: Long, listener: Animator.AnimatorListener? = null) {
    if (animations.isEmpty())
        return
    val animatorSet = AnimatorSet()
    val builder = animatorSet.setDuration(duration)
            .play(animations[0])
    animations.filterIndexed { index, _ -> index != 0 }
            .forEach { builder.with(it) }
    if (listener != null)
        animatorSet.addListener(listener)
    animatorSet.start()

}

/**
 * 平移旋转渐变组合动画
 */
fun View.transRotateAlpha(transX: FloatArray = floatArrayOf(0f, 0f), transY: FloatArray = floatArrayOf(0f, 0f),
                          rotation: FloatArray = floatArrayOf(this.rotation, this.rotation),
                          alpha: FloatArray = floatArrayOf(this.alpha, this.alpha), duration: Long = 500,
                          listener: Animator.AnimatorListener? = null) {
    this.animer(transXAnimation(*transX), transYAnimation(*transY), rotationAnimation(*rotation),
            alphaAnimation(*alpha), duration = duration, listener = listener)
}