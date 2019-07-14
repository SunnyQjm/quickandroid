package cn.qjm253.quick_android_base.util

import java.util.concurrent.atomic.AtomicInteger

/**
 * @author SunnyQjm
 * @date 19-7-14 下午8:16
 */

object GeneratedId {
    private val sNextGeneratedId = AtomicInteger(20000)

    /**
     * Generate a value suitable for use in setId
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    fun generateViewId(): Int {
        while (true) {
            val result = sNextGeneratedId.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result
            }
        }
    }
}