package cn.qjm253.quick_android_custom_view.custom_drawable_size_view

/**
 * 这是一个处理drawable自定义大小设置的工具类
 *
 * 1. 四个方向的drawable都有一个默认大小 DRAWABLE_DEFAULT_SIZE；
 *
 * 2. 使用 'drawable_width' 和 'drawable_height' 来设置全局大小，则四个方向的drawable均被设置；
 *
 * 3. 如果使用 'left_drawable_width' 和 'left_drawable_height' 这种单独设置某个方向的drawable会覆盖默认大小和全局大小
 *
 * @author SunnyQjm
 * @date 19-7-10 下午9:00
 */
class SizeWrap(var width: Int = -1, var height: Int = -1) {

    /**
     * 检查用户是否自定义了drawable的size，按上述规则进行判断
     */
    fun checkSize(globalWidth: Int, globalHeight: Int, localWidth: Int, localHeight: Int) {
        width = DrawableTextView.DRAWABLE_DEFAULT_SIZE
        height = DrawableTextView.DRAWABLE_DEFAULT_SIZE

        // 对某一个具体方向图标大小的设置会覆盖对全局图标大小的设置
        if (localHeight > 0 && localWidth > 0) {
            width = localWidth
            height = localHeight
        }


        if (localHeight == DrawableTextView.DRAWABLE_DEFAULT_SIZE && localWidth == DrawableTextView.DRAWABLE_DEFAULT_SIZE
            && globalHeight > 0 && globalWidth > 0) {
            width = globalWidth
            height = globalHeight
        }
    }
}