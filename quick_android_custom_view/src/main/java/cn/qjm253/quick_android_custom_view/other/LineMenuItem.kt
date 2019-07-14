package cn.qjm253.quick_android_custom_view.other

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import cn.qjm253.quick_android_base.util.GeneratedId
import cn.qjm253.quick_android_custom_view.R

/**
 * 菜单（可以设置图标）
 *
 * @author SunnyQjm
 * @date 19-7-14 下午8:14
 */

class LineMenuItem @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr), LineMenu {

    private var myContext: Context? = null
    private var menuTitle: String? = null
    private var menuValue: String? = null
    private var menuTitleSize: Float = 0.toFloat()
    @ColorInt
    private var menuTitleColor: Int = 0
    private var menuValueSize: Float = 0.toFloat()
    @ColorInt
    private var menuValueColor: Int = 0
    @DrawableRes
    private var leftResource: Int = 0
    private var rightResource: Int = 0

    private var isRightIconVisible: Boolean = false
    private var isLeftIconVisible: Boolean = false

    private var leftIconSize: Int = 0
    private var rightIconSize: Int = 0

    private var leftMargin: Int = 0
    private var rightMargin: Int = 0
    private var leftIconTextSpan: Int = 0

    private var leftIcon: ImageView? = null
    private var rightIcon: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvValue: TextView? = null

    init {
        this.myContext = context
        getAttr(context, attrs, defStyleAttr)
        initView()
    }

    private fun getAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LineMenuItem)
        menuTitle = ta.getString(R.styleable.LineMenuItem_menu_title)
        menuValue = ta.getString(R.styleable.LineMenuItem_menu_value)
        menuTitleSize = px2sp(context,
            ta.getDimensionPixelSize(R.styleable.LineMenuItem_menu_title_size,
                sp2px(context, DEFAULT_TEXT_SIZE.toFloat())).toFloat()).toFloat()
        menuTitleColor = ta.getColor(R.styleable.LineMenuItem_menu_title_color,
            resources.getColor(DEFAULT_TITLE_COLOR))
        menuValueSize = px2sp(context,
            ta.getDimension(R.styleable.LineMenuItem_menu_value_size,
                sp2px(context, DEFAULT_TEXT_SIZE.toFloat()).toFloat())).toFloat()
        menuValueColor = ta.getColor(R.styleable.LineMenuItem_menu_value_color,
            resources.getColor(DEFAULT_TITLE_COLOR))
        leftResource = ta.getResourceId(R.styleable.LineMenuItem_left_resource, DEFAULT_LEFT_RESOURCE)
        rightResource = ta.getResourceId(R.styleable.LineMenuItem_right_resource, DEFAULT_RIGHT_RESOURCE)
        leftIconSize = ta.getDimensionPixelSize(R.styleable.LineMenuItem_left_icon_size,
            dip2px(context, DEFAULT_LEFT_ICON_SIZE.toFloat()))
        rightIconSize = ta.getDimensionPixelSize(R.styleable.LineMenuItem_right_icon_size,
            dip2px(context, DEFAULT_RIGHT_ICON_SIZE.toFloat()))
        isRightIconVisible = ta
            .getBoolean(R.styleable.LineMenuItem_is_right_icon_visible, true)
        isLeftIconVisible = ta
            .getBoolean(R.styleable.LineMenuItem_is_left_icon_visible, false)
        leftMargin = ta.getDimensionPixelSize(R.styleable.LineMenuItem_left_margin, dip2px(context, DEFAULT_MARGIN))
        rightMargin = ta.getDimensionPixelOffset(R.styleable.LineMenuItem_right_margin, dip2px(context, DEFAULT_MARGIN))
        leftIconTextSpan = ta.getDimensionPixelOffset(R.styleable.LineMenuItem_left_icon_text_span,
            dip2px(context, DEFAULT_MARGIN))
        ta.recycle()
    }

    private fun initView() {
        leftIcon = ImageView(myContext)
        leftIcon!!.setImageResource(leftResource)
        leftIcon!!.scaleType = ImageView.ScaleType.FIT_CENTER
        leftIcon!!.setBackgroundResource(R.color.transparent)
        val left_icon_param = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        left_icon_param.addRule(CENTER_VERTICAL)
        left_icon_param.width = leftIconSize
        left_icon_param.height = leftIconSize
        left_icon_param.leftMargin = leftMargin
        leftIcon!!.layoutParams = left_icon_param
        addView(leftIcon, left_icon_param)
        if (!isLeftIconVisible) {
            leftIcon!!.visibility = View.GONE
        }
        val left_icon_id = GeneratedId.generateViewId()
        leftIcon!!.id = left_icon_id

        tvTitle = TextView(myContext)
        tvTitle!!.text = menuTitle
        tvTitle!!.setTextColor(menuTitleColor)
        tvTitle!!.textSize = menuTitleSize
        tvTitle!!.setBackgroundResource(R.color.transparent)
        val title_param = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        title_param.addRule(CENTER_VERTICAL)
        title_param.leftMargin = leftIconTextSpan
        title_param.addRule(RIGHT_OF, left_icon_id)
        tvTitle!!.layoutParams = title_param
        addView(tvTitle, title_param)


        rightIcon = ImageView(myContext)
        rightIcon!!.setImageResource(rightResource)
        rightIcon!!.scaleType = ImageView.ScaleType.FIT_CENTER
        rightIcon!!.setBackgroundResource(R.color.transparent)
        val right_icon_param = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        right_icon_param.addRule(CENTER_VERTICAL)
        right_icon_param.addRule(ALIGN_PARENT_RIGHT)
        right_icon_param.width = rightIconSize
        right_icon_param.height = rightIconSize
        right_icon_param.rightMargin = rightMargin
        rightIcon!!.layoutParams = right_icon_param
        addView(rightIcon, right_icon_param)
        val right_icon_id = GeneratedId.generateViewId()
        rightIcon!!.id = right_icon_id

        tvValue = TextView(myContext)
        tvValue!!.text = menuValue
        tvValue!!.setTextColor(menuValueColor)
        tvValue!!.textSize = menuValueSize
        tvValue!!.setBackgroundResource(R.color.transparent)
        val tv_value_param = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        tv_value_param.addRule(CENTER_VERTICAL)
        if (isRightIconVisible)
            tv_value_param.addRule(LEFT_OF, right_icon_id)
        else
            tv_value_param.addRule(ALIGN_PARENT_RIGHT)
        tv_value_param.rightMargin = dip2px(myContext!!, DEFAULT_MARGIN)
        tvValue!!.layoutParams = tv_value_param
        addView(tvValue, tv_value_param)

        if (isRightIconVisible)
            setRightIconVisible(View.VISIBLE)
        else
            setRightIconVisible(View.INVISIBLE)
    }

    override fun getLeft_icon(): ImageView? {
        return leftIcon
    }

    override fun getRight_icon(): ImageView? {
        return rightIcon
    }

    override fun getTv_title(): TextView? {
        return tvTitle
    }

    override fun getTv_value(): TextView? {
        return tvValue
    }

    fun value(): String {
        return getTv_value()?.text?.toString() ?: ""
    }

    fun title(): String {
        return getTv_title()?.text?.toString() ?: ""
    }

    override fun setLeftIcon(@DrawableRes res: Int) {
        this.post {
            leftResource = res
            leftIcon!!.setImageResource(leftResource)
        }
    }

    override fun setRightIcon(@DrawableRes res: Int) {
        this.post {
            rightResource = res
            rightIcon!!.setImageResource(rightResource)
        }
    }

    override fun setRightIconVisible(visible: Int) {
        if (rightIcon != null)
            rightIcon!!.visibility = visible
    }

    override fun setLeftIconVisible(visible: Int) {
        if (leftIcon != null)
            leftIcon!!.visibility = visible
    }

    override fun setTitleIconVisible(visible: Int) {
        if (tvTitle != null)
            tvTitle!!.visibility = visible
    }

    override fun setValueIconVisible(visible: Int) {
        if (tvValue != null)
            tvValue!!.visibility = visible
    }

    override fun setTitle(title: String) {
        if (tvTitle != null)
            tvTitle!!.text = title
    }

    override fun setValue(value: String) {
        if (tvValue != null)
            tvValue!!.text = value
    }

    companion object {

        private val DEFAULT_TEXT_SIZE = 14
        private val DEFAULT_MARGIN = 13f

        private val DEFAULT_TITLE_COLOR = R.color.text_gray
        private val DEFAULT_LEFT_RESOURCE = R.drawable.menu
        private val DEFAULT_RIGHT_RESOURCE = R.drawable.icon_arrow
        private val DEFAULT_LEFT_ICON_SIZE = 24
        private val DEFAULT_RIGHT_ICON_SIZE = 24
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}